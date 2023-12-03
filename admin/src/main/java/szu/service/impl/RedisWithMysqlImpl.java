package szu.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.util.RandomUtil;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import szu.common.service.RedisService;
import szu.dao.*;
import szu.model.*;
import szu.service.RedisWithMysql;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RedisWithMysqlImpl
 * @Description: Redis和Mysql同步实现类
 * @Version 1.0
 * @Date: 2023-11-29 9:04
 * @Auther: UserXin
 */
@Service
public class RedisWithMysqlImpl implements RedisWithMysql {
    @Resource
    private UpdateDao updateDao;

    @Resource
    private VideoDao videoDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private PartitionDao partitionDao;

    @Resource
    private UpdateHeatDao updateHeatDao;

    @Resource
    private RedisService redisService;

    /**
     * 初始化将数据库所有的update及其关联的数据放入到redis中
     */
    @Override
    public void initRedis() {
        List<Update> allUpdate = updateDao.findAll();
        if(allUpdate==null) return;
        for (Update update : allUpdate) {
            synUpdate(update);
        }
    }


    /**
     * 更新redis指定位置数据（在缓存未命中的时候调用该方法将数据放入缓存中）
     * @param updateId
     * @return
     */
    public boolean updateRedisByUpdateId(Integer updateId){
        if(updateId==null) throw new NullPointerException();
        Update update = updateDao.findById(updateId);
        if(update!=null){
            synUpdate(update);
            return true;
        }else{
            return false;
        }

    }

    /**
     * 更新redis指定位置数据（在缓存未命中的时候调用该方法将数据放入缓存中）
     * @param videoId
     * @return
     */
    public boolean updateRedisByVideoId(Integer videoId){
        if(videoId==null) throw new NullPointerException();
        Update update = updateDao.findByVid(videoId);
        if(update!=null){
            synUpdate(update);
            return true;
        }else{
            return false;
        }

    }


    /**
     * 将update及其关联的信息放入redis
     * @param update
     */
    private void synUpdate(Update update) {
        if(update.getVid()==0){  //为图文动态
            String key = "updates:photoUpdate:"+ update.getId();
            //update表
            redisService.hSet(key,"uid", update.getUid());
            redisService.hSet(key,"content", update.getContent());
            redisService.hSet(key,"status", update.getStatus());
            redisService.hSet(key,"uploadTime", update.getUploadTime());
            redisService.hSet(key,"urls", update.getUrls());

            //update_heat表
            UpdateHeat updateHeat = updateHeatDao.getByUpdateId(update.getId());
            redisService.hSet(key,"likeNum",updateHeat.getLikeNum());
            redisService.hSet(key,"commentNum",updateHeat.getCommentNum());
            redisService.hSet(key,"shareNum",updateHeat.getShareNum());

            //User表
            User userById = userInfoDao.getUserById(update.getUid());
            redisService.hSet(key,"upName",userById.getName());
            long exTime = RandomUtil.randomInt(1800,7200);
            redisService.expire(key, exTime);//时间的单位是秒
        }else{
            String key = "updates:videoUpdate:"+ update.getId();
            //video表
            Video video = videoDao.findById(update.getVid());
            redisService.hSet(key,"title",video.getTitle());
            redisService.hSet(key,"playNum",video.getPlayNum());
            redisService.hSet(key,"dmNum",video.getDmNum());
            redisService.hSet(key,"totalTime",video.getTotalTime());
            redisService.hSet(key,"pid",video.getPid());
            redisService.hSet(key,"coinNum",video.getCoinNum());
            redisService.hSet(key,"starNum",video.getStarNum());
            redisService.hSet(key,"url",video.getUrl());


            //Partition表
            Partition partition = partitionDao.findById(video.getPid());
            redisService.hSet(key,"pName",partition.getName());

            //update表
            redisService.hSet(key,"uid", update.getUid());
            redisService.hSet(key,"uploadTime", update.getUploadTime());
            redisService.hSet(key,"coverUrl", update.getUrls());

            //update_heat表
            UpdateHeat updateHeat = updateHeatDao.getByUpdateId(update.getId());
            redisService.hSet(key,"likeNum",updateHeat.getLikeNum());
            redisService.hSet(key,"commentNum",updateHeat.getCommentNum());
            redisService.hSet(key,"shareNum",updateHeat.getShareNum());

            //User表
            User userById = userInfoDao.getUserById(update.getUid());
            redisService.hSet(key,"upName",userById.getName());
            //设置三十分钟到两小时之间的过期时间
            long exTime = RandomUtil.randomInt(1800,7200);
            redisService.expire(key, exTime);//时间的单位是秒
        }
    }

}
