package szu.service;

import szu.model.Star;
import szu.model.StarVideo;
import szu.vo.StarVo;

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
     * @param updateId 要收藏的视频的动态id
     * @return
     */
    boolean starVideo(List<String> sids, Integer updateId);

    /**
     * 根据用户uid获取收藏夹列表
     * @param uid 当前登录的用户的id
     * @return
     */
    List<Star> listStarByUid(Integer uid);

    /**
     * 根据收藏夹id分页获取收藏夹内容
     * @param sid 收藏夹id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    List<StarVo> listStarContentBySidByPage(String sid, Integer page, Integer size);

    /**
     * 获取当前观看的视频被当前用户收藏在哪个收藏夹下
     * @param uid 当前登录的用户的id
     * @param updateId 当前观看视频的动态id
     * @return
     */
    List<String> listStared(Integer uid, Integer updateId);

    /**
     * 删除收藏的视频
     * @param sid 收藏夹id
     * @param updateId 要删除的视频的动态id
     * @return
     */
    boolean removeStarVideo(String sid, Integer updateId);

    /**
     * 删除收藏夹
     * @param sid 收藏夹id
     * @return
     */
    boolean removeStar(String sid);
}
