package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import szu.model.Comment;

/**
 * @description CommentDao
 * @author UserXin
 * @date 2023-11-01
 */
@Mapper
public interface CommentDao {
    /**
     * 添加评论
     * @param comment
     */
    void addComment(Comment comment);
}
