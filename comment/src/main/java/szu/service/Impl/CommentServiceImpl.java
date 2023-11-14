package szu.service.Impl;

import org.springframework.stereotype.Service;
import szu.dao.CommentDao;
import szu.model.Comment;
import szu.service.CommentService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description CommentDao
 * @author UserXin
 * @date 2023-11-01
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    /**
     * 添加评论
     * @param comment
     */
    @Override
    public void addComment(Comment comment) {
        //设置创建时间
        comment.setCreateTime(LocalDateTime.now());
        commentDao.addComment(comment);
    }


    /**
     * 根据foreignId和foreignType获取指定区域的评论
     * @param foreignId
     * @param foreignType
     * @return
     */
    @Override
    public List<Comment> getCommentsByForeignIdAndForeignType(Integer foreignId, Integer foreignType) {
        List<Comment> comments = commentDao.getCommentsByForeignIdAndForeignType(foreignId,foreignType);
        return comments;
    }
}
