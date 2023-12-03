package szu.service;

import szu.model.Comment;
import szu.vo.CommentVo;

import java.util.List;

/**
 * @ClassName: LikeService
 * @Description: 点赞
 * @Version 1.0
 * @Date: 2023-11-22 19:05
 * @Auther: UserXin
 */
public interface LikeService {
    /**
     * 点赞
     * @param uid 当前登录用户id
     * @param fid 要点赞的（评论|动态）id
     * @param fUid 要点赞的（评论|动态）的用户id
     * @return
     */
    boolean like(Integer uid,String fid,Integer fUid,Integer flag);

    /**
     * 插入一个用户点赞列表
     * @param uid 用户id
     * @return
     */
    boolean insertLikeUser(Integer uid);

}
