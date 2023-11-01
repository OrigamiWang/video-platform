package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import szu.model.Comment;

import java.util.List;

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

    /**
     * 根据foreignId和foreignType获取指定区域的评论
     * @param foreignId
     * @param foreignType
     * @return
     */
    List<Comment> getCommentsByForeignIdAndForeignType(Integer foreignId, Integer foreignType);
}
