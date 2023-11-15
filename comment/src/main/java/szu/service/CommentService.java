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
     * 根据foreignId分页获取指定区域的评论，根据点赞数排序
     * @param foreignId 要获取评论的动态id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    List<Comment> getCommentsByForeignIdAndPages(Integer foreignId,int page,int size);

    /**
     * 分页获取对应根评论下的子评论
     * @param pid 要获取子评论根评论id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    List<Comment> listChildrenCommentByPages(String pid, int page, int size);

    /**
     * 回复评论
     * @param comment
     * @param pid
     */
    void replyComment(Comment comment,String pid);

    /**
     * 点赞评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的根评论的id
     * @return
     */
    void likeRootComment(Integer flag, String pid);

    /**
     * 点赞子评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的对应的根评论id
     * @param cid 点赞的子评论id
     * @return
     */
    void likeChildrenComment(Integer flag, String pid, String cid);


    /**
     * 删除根评论
     * @param pid 要删除的根评论的id
     * @return
     */
    void deleteRootComment(String pid);

    /**
     * 删除子评论
     * @param pid 要删除的子评论的根评论的id
     * @param cid 要删除的子评论的id
     * @return
     */
    void deleteChildComment(String pid, String cid);

}
