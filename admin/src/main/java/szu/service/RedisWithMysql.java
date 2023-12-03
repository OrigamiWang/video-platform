package szu.service;

import szu.dao.UpdateDao;
import szu.dao.VideoDao;

import javax.annotation.Resource;

/**
 * @ClassName: RedisWithMysql
 * @Description: Redis和Mysql同步
 * @Version 1.0
 * @Date: 2023-11-29 8:58
 * @Auther: UserXin
 */
public interface RedisWithMysql {
    void initRedis();

    /**
     * 更新redis指定位置数据（在缓存未命中的时候调用该方法将数据放入缓存中）
     * @param updateId
     * @return
     */
    boolean updateRedisByUpdateId(Integer updateId);

    /**
     * 更新redis指定位置数据（在缓存未命中的时候调用该方法将数据放入缓存中）
     * @param videoId
     * @return
     */
    boolean updateRedisByVideoId(Integer videoId);
}
