package szu.service;

import szu.model.Comment;


import java.util.List;

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
//    void addComment(Comment01 comment);
    void addComment(Comment comment);

//    /**
//     * 根据foreignId和foreignType获取指定区域的评论
//     * @param foreignId
//     * @param foreignType
//     * @return
//     */
//    List<Comment01> getCommentsByForeignIdAndForeignType(Integer foreignId, Integer foreignType);

    /**
     * 根据foreignId获取指定动态的评论
     * @param foreignId
     * @return
     */
    List<Comment> getCommentsByForeignId(Integer foreignId);

    /**
     * 回复评论
     * @param comment
     * @param pid
     */
    void replyComment(Comment comment,String pid);

    /**
     * 点赞评论
     * @param flag
     */
    void likeComment(Integer flag,String pid,Integer index);
}
