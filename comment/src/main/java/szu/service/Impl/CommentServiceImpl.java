package szu.service.Impl;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import szu.common.exception.Asserts;
import szu.dao.CommentRepository;

import szu.model.Comment;
import szu.model.Like;
import szu.service.CommentService;
import szu.vo.CommentVo;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @description CommentDao
 * @author UserXin
 * @date 2023-11-01
 */

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MongoTemplate mongoTemplate;


    private Comment initComment(Comment comment){
        if (comment == null) throw new NullPointerException(); //安全性检查
        if (comment.getTargetUsername() == null) comment.setTargetUsername("");
        if (comment.getUsername() == null) comment.setUsername("");
        comment.setReplyNum(0);
        comment.setLikeNum(0);
        comment.setIsTop(0);
        //设置创建时间
        comment.setCreateTime(LocalDateTime.now());
        return comment;
    }

    /**
     * 给评论加上是否点赞的标签封装为vo返回
     * @param uid
     * @param commentList
     * @return
     */
    public List<CommentVo> getCommentVoListWithLike(Integer uid, List<Comment> commentList) {
        Query query = Query.query(Criteria.where("uid").is(uid)); //找到当前登录的用户的点赞列表
        Like like = mongoTemplate.findOne(query, Like.class);
        HashMap<String, Integer> fidMap = like.getFidMap();
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            if(fidMap.containsKey(comment.getId())){  //在点赞列表里面，说明该用户点赞
                commentVo.setIsLiked(true); //设置已点赞
            }else {
                commentVo.setIsLiked(false); //设置未点赞
            }
            commentVoList.add(commentVo);
        }
        return commentVoList;
    }

    /**
     * 添加评论
     * @param comment
     */
    @Override
    public void addComment(Comment comment) {
        comment = initComment(comment);
        commentRepository.save(comment);
    }

    @Override
    public Long countCommentsByForeignId(Integer foreignId) {
        Comment exampleComment = new Comment();
        exampleComment.setForeignId(foreignId); // 设置查询条件
        Example<Comment> example = Example.of(exampleComment);
        return commentRepository.count(example);
    }

    /**
     * 根据foreignId分页获取指定区域的评论，根据点赞数排序
     * @param uid 当前登录的用户id
     * @param foreignId 要获取评论的动态id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @Override
    public List<CommentVo> getCommentsByForeignIdAndPages(Integer uid,Integer foreignId, int page, int size, String sortBy) {
        if(foreignId<0||page<0||size<0) throw new IllegalArgumentException(); //安全性检查
        Query query = Query.query(Criteria.where("foreignId").is(foreignId));
        //根据点赞数量降序分页查询
//        Asserts.isTrue(sortBy.equals("likeNum") || sortBy.equals("createTime"), "sortBy参数错误");
        //如果有置顶的那么置顶的就是第一个
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("isTop")).and(Sort.by(Sort.Order.desc(sortBy))));
        query.with(pageRequest);
        //限制第一次只查出三条子评论，后续的通过分页获取
        query.fields().slice("children", 3);
        List<Comment> Comments = mongoTemplate.find(query, Comment.class);

        //加上点赞封装为vo返回
        List<CommentVo> commentVos = getCommentVoListWithLike(uid, Comments);
        for (int i = 0; i < Comments.size(); i++) {
            commentVos.get(i).setChildren(getCommentVoListWithLike(uid,Comments.get(i).getChildren()));
        }
        return commentVos;
    }

    @Override
    public Long countChildCommentsByPid(String pid) {
        // 构建聚合操作，匹配指定的评论（根据评论的id）
        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(pid));

        // 拆分子评论数组，并计算数组大小
        AggregationOperation project = Aggregation.project()
                .and(ArrayOperators.Size.lengthOfArray("$children")).as("childCommentsCount");

        // 执行聚合操作
        Aggregation aggregation = Aggregation.newAggregation(match, project);
        AggregationResults<CommentChildCount> result = mongoTemplate.aggregate(aggregation, "comment", CommentChildCount.class);

        // 获取结果
        CommentChildCount commentChildCount = result.getUniqueMappedResult();
        return commentChildCount != null ? commentChildCount.getChildCommentsCount() : 0;
    }

    // 内部类用于映射聚合结果
    @Data
    private static class CommentChildCount {
        private Long childCommentsCount;
    }

    /**
     * 分页获取对应根评论下的子评论
     *
     * @param pid  要获取子评论根评论id
     * @param page 当前页
     * @param size 每页大小
     * @param uid 当前登录用户id
     * @return
     */
    @Override
    public List<CommentVo> listChildrenCommentByPages(String pid, int page, int size, Integer uid) {
        //安全性检查
        if(page<0||size<0) throw new IllegalArgumentException();
        if(pid==null) throw new NullPointerException();
        // 构建聚合管道
        //匹配根评论id
        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(pid));
        //取出children
        AggregationOperation project = Aggregation.project("children").andExclude("_id");
        //将children展开，一条数据拆分成多条
        AggregationOperation unwind = Aggregation.unwind("children");
        //分页
        AggregationOperation skip = Aggregation.skip(page * size);
        AggregationOperation limit = Aggregation.limit(size);
        //将子评论作为新的根文档返回
        AggregationOperation replaceRoot = Aggregation.replaceRoot("children");

        // 执行聚合管道
        Aggregation aggregation = Aggregation.newAggregation(match, project, unwind, skip, limit, replaceRoot);
        List<Comment> results = mongoTemplate.aggregate(aggregation, "comment", Comment.class).getMappedResults();
        //加上点赞封装为vo返回
        return getCommentVoListWithLike(uid,results);
    }

    /**
     * 回复评论
     * @param comment
     * @param pid
     */
    @Override
    public void replyComment(Comment comment,String pid) {
        if(comment==null||pid==null) throw new NullPointerException(); //安全性检查
        comment = initComment(comment);
        Query query = Query.query(Criteria.where("_id").is(pid)); //找到被评论的评论
        // 为子评论生成唯一的id
        comment.setId(ObjectId.get().toString());
        Update update=new Update();
        //追加一条评论
        update.push("children",comment);
        update.inc("replyNum",1);
        //更新json
        mongoTemplate.updateFirst(query,update,"comment");
    }

    /**
     * 点赞评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的根评论的id
     * @return
     */
    @Override
    public void likeRootComment(Integer flag, String pid) {
        //安全性检查
        if(flag!=-1&&flag!=1) throw new IllegalArgumentException();
        if(pid==null) throw new NullPointerException();
        Query query = Query.query(Criteria.where("_id").is(pid)); //找到被评论的评论
        Update update=new Update();
        //修改点赞数
        update.inc("likeNum",flag);
        //更新评论
        mongoTemplate.updateFirst(query,update,"comment");
    }

    /**
     * 点赞子评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的对应的根评论id
     * @param cid 点赞的子评论id
     * @return
     */
    @Override
    public void likeChildrenComment(Integer flag, String pid, String cid) {
        //安全性检查
        if(flag!=-1&&flag!=1) throw new IllegalArgumentException();
        if(pid==null&&cid==null) throw new NullPointerException();
        Query query = Query.query(Criteria.where("_id").is(pid).and("children._id").is(cid)); //找到被点赞的评论
        Update update = new Update();
        //修改点赞数
        update.inc("children.$.likeNum",flag);
        //更新评论
        mongoTemplate.updateFirst(query,update,Comment.class);
    }

    /**
     * 删除根评论
     * @param pid 要删除的根评论的id
     * @return
     */
    @Override
    public void deleteRootComment(String pid) {
        Query query = Query.query(Criteria.where("_id").is(pid)); //找到要删除的评论
        mongoTemplate.remove(query,Comment.class);//删除评论
    }

    /**
     * 删除子评论
     * @param pid 要删除的子评论的根评论的id
     * @param cid 要删除的子评论的id
     * @return
     */
    @Override
    public void deleteChildComment(String pid, String cid) {
        //找到删除的根评论
        Query query = Query.query(Criteria.where("_id").is(pid));
        Update update = new Update();
        //创建要删除的条件
        Document doc = new Document();
        //删除子评论id为cid的评论
        doc.put("_id", cid);
        update.pull("children", doc);
        update.inc("replyNum",-1);
        mongoTemplate.updateFirst(query,update,Comment.class);
    }

    /**
     * 置顶评论
     * @param pid 要置顶的评论id（只能为根评论）
     * @param flag 置顶或取消置顶，1：置顶 0：取消置顶
     * @return
     */
    @Override
    public void toTopComment(String pid,Integer flag) {
        Query query = Query.query(Criteria.where("_id").is(pid));
        Update update = new Update();
        update.set("isTop",flag);
        mongoTemplate.updateFirst(query,update,Comment.class);
    }

}
