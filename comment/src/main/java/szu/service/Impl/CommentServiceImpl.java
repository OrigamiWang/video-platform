package szu.service.Impl;

import org.springframework.stereotype.Service;
import szu.dao.CommentDao;
import szu.model.Comment;
import szu.service.CommentService;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
}
