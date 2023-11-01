package szu.service;

import org.springframework.stereotype.Service;
import szu.model.Comment;

/**
 * @description CommentDao
 * @author UserXin
 * @date 2023-11-01
 */

public interface CommentService {
    /**
     * 添加评论
     * @param comment
     */
    void addComment(Comment comment);
}
