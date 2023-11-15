package szu.service.Impl;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import szu.dao.CommentRepository;

import szu.model.Comment;
import szu.service.CommentService;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

/**
 * @description CommentDao
 * @author UserXin
 * @date 2023-11-01
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MongoTemplate mongoTemplate;


    /**
     * 添加评论
     * @param comment
     */
    @Override
    public void addComment(Comment comment) {

        if(comment==null) throw new NullPointerException();//安全性检查
        //设置创建时间
        comment.setCreateTime(LocalDateTime.now());
        commentRepository.save(comment);
    }

    /**
     * 根据foreignId分页获取指定区域的评论，根据点赞数排序
     * @param foreignId 要获取评论的动态id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @Override
    public List<Comment> getCommentsByForeignIdAndPages(Integer foreignId,int page,int size) {
        if(foreignId<0||page<0||size<0) throw new IllegalArgumentException(); //安全性检查
        Query query = Query.query(Criteria.where("foreignId").is(foreignId));
        //根据点赞数量降序分页查询
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("likeNum")));
        query.with(pageRequest);
        //限制第一次只查出三条子评论，后续的通过分页获取
        query.fields().slice("children", 3);
        List<Comment> Comments = mongoTemplate.find(query, Comment.class);
        return Comments;
    }

    /**
     * 分页获取对应根评论下的子评论
     * @param pid 要获取子评论根评论id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @Override
    public List<Comment> listChildrenCommentByPages(String pid, int page, int size) {
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
        return results;
    }

    /**
     * 回复评论
     * @param comment
     * @param pid
     */
    @Override
    public void replyComment(Comment comment,String pid) {
        if(comment==null||pid==null) throw new NullPointerException(); //安全性检查
        Query query = Query.query(Criteria.where("_id").is(pid)); //找到被评论的评论
        //追加一条评论
        comment.setCreateTime(LocalDateTime.now());
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
}
