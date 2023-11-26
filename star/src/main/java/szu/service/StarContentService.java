package szu.service;

import szu.model.StarContent;

import java.util.List;

/**
 * @ClassName: StarContentService
 * @Description:  收藏夹内容Service接口
 * @Version 1.0
 * @Date: 2023-11-23 19:36
 * @Auther: UserXin
 */
public interface StarContentService {

    /**
     * 根据收藏夹id分页获取收藏夹内容
     * @param sid 收藏夹id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    List<StarContent> listStarContentBySidByPage(Integer sid, Integer page, Integer size);
}
