package szu.service.Impl;

import org.springframework.data.mongodb.core.MongoTemplate;
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
        //设置创建时间
        comment.setCreateTime(LocalDateTime.now());
        commentRepository.save(comment);
    }

    /**
     * 根据foreignId获取指定动态的评论
     * @param foreignId
     * @return
     */
    @Override
    public List<Comment> getCommentsByForeignId(Integer foreignId) {
        Query query = Query.query(Criteria.where("foreignId").is(foreignId));
        List<Comment> Comments = mongoTemplate.find(query, Comment.class);
        Comments.sort((o1, o2) -> o1.getLikeNum()-o2.getLikeNum()); //按点赞数排序
        return Comments;
    }

    /**
     * 回复评论
     * @param comment
     * @param pid
     */
    @Override
    public void replyComment(Comment comment,String pid) {
        Query query = Query.query(Criteria.where("_id").is(pid)); //找到被评论的评论
        Comment pComment = mongoTemplate.findOne(query, Comment.class, "comment");
        //获取该评论的子评论列表
        List<Comment> children = pComment.getChildren();
        //追加一条评论
        comment.setCreateTime(LocalDateTime.now());
        if(children!=null&&children.get(0)!=null){
            children.add(comment);
        }else{
            children = new ArrayList<>();
            children.add(comment);
        }
        Update update=new Update();
        update.set("children",children);
        update.inc("replyNum",1);
        //更新json
        mongoTemplate.updateFirst(query,update,"comment");
    }

    @Override
    public void likeComment(Integer flag,String pid,Integer index) {
        Query query = Query.query(Criteria.where("_id").is(pid)); //找到被评论的评论
        Comment pComment = mongoTemplate.findOne(query, Comment.class, "comment");
        //获取该评论的子评论列表
        List<Comment> children = pComment.getChildren();
        Update update=new Update();
        if(index==-1){   //点赞的是根评论
            update.inc("likeNum",flag);
        }else{   //点赞的是子评论
            Comment comment = children.get(index);
            comment.setLikeNum(comment.getLikeNum()+flag);
            children.set(index,comment);
            update.set("children",children);
        }
        mongoTemplate.updateFirst(query,update,"comment");
    }


}
