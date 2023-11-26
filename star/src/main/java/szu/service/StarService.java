package szu.service;

import szu.model.Star;

import java.util.List;

/**
 * @ClassName: StarService
 * @Description:  收藏夹Service接口
 * @Version 1.0
 * @Date: 2023-11-23 19:36
 * @Auther: UserXin
 */
public interface StarService {

    /**
     * 添加收藏夹
     * @param uid 当前登录用户id
     * @param starName 收藏夹名称
     * @return
     */
    boolean addStar(Integer uid, String starName);

    /**
     * 收藏视频
     * @param sids 收藏夹id集合
     * @param vid 要收藏的视频id
     * @return
     */
    boolean starVideo(List<Integer> sids, Integer vid);

    /**
     * 根据用户uid获取收藏夹列表
     * @param uid 当前登录的用户的id
     * @return
     */
    List<Star> listStarByUid(Integer uid);


}
