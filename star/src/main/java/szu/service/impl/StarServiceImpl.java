package szu.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import szu.common.service.RedisService;
import szu.dao.StarDao;
import szu.model.Star;
import szu.model.StarVideo;
import szu.service.StarService;
import szu.vo.StarVo;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: StarServiceImpl
 * @Description: 收藏夹Service接口实现类
 * @Version 1.0
 * @Date: 2023-11-23 19:38
 * @Auther: UserXin
 */
@Service
public class StarServiceImpl implements StarService {

    @Resource
    private StarDao starDao;


    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisService redisService;

    /**
     * 添加收藏夹
     *
     * @param uid      当前登录用户id
     * @param starName 收藏夹名称
     * @return
     */
    @Override
    public boolean addStar(Integer uid, String starName) {
        if(uid<0) throw new IllegalArgumentException();
        if (starName==null) throw new NullPointerException();
        Star star = new Star();
        star.setUid(uid);
        star.setStarName(starName);
        star.setStarNum(0);
        star.setStarVideos(new ArrayList<>());
        mongoTemplate.save(star);
        return true;

    }

    /**
     * 收藏视频
     * @param sids 收藏夹id集合
     * @param updateId 要收藏的视频的动态id
     * @return
     */
    @Override
    public boolean starVideo(List<String> sids, Integer updateId) {
        if(updateId<0 || sids==null || sids.size()==0) throw new IllegalArgumentException();
        int successNum = 0;
        for (String sid : sids) {
            //设置收藏夹收藏数量加一
            Query query = Query.query(Criteria.where("_id").is(sid));
            Update update = new Update();
            update.inc("starNum",1);
            //添加视频在收藏夹内
            StarVideo starVideo = new StarVideo(updateId, LocalDateTime.now());
            update.push("starVideos", starVideo);
            mongoTemplate.updateFirst(query,update,Star.class);
            successNum++;
        }
       if(successNum<sids.size()) {
           return false;
       } else {
           return true;
       }
    }

    /**
     * 根据用户uid获取收藏夹列表
     * @param uid 当前登录的用户的id
     * @return
     */
    @Override
    public List<Star> listStarByUid(Integer uid) {
        if(uid<0) throw new IllegalArgumentException();
        Query query = Query.query(Criteria.where("uid").is(uid));
        query.fields().slice("starVideos", 0);
        List<Star> stars = mongoTemplate.find(query, Star.class);
        return stars;
    }


    /**
     * 根据收藏夹id分页获取收藏夹内容
     * @param sid 收藏夹id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @Override
    public List<StarVo> listStarContentBySidByPage(String sid, Integer page, Integer size) {
        if("".equals(sid)||page<0||size<=0) throw new IllegalArgumentException();
        //匹配对应收藏夹
        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(sid));
        //提取出收藏夹内容
        AggregationOperation project = Aggregation.project("starVideos").andExclude("_id");
        //把收藏内容一行内容拆开为多行
        AggregationOperation unwind = Aggregation.unwind("starVideos");
        //分页
        AggregationOperation skip = Aggregation.skip(page*size);
        AggregationOperation limit = Aggregation.limit(size);
        //将收藏夹内容作为新的根文档返回
        AggregationOperation replaceRoot = Aggregation.replaceRoot("starVideos");

        // 执行聚合管道
        Aggregation aggregation = Aggregation.newAggregation(match, project, unwind, skip, limit,replaceRoot);
        List<StarVideo> results = mongoTemplate.aggregate(aggregation, "star", StarVideo.class).getMappedResults();
        String prefix = "updates:videoUpdate:";
        List<StarVo> starVos = new ArrayList<>();
        for (StarVideo result : results) {
            Integer updateId = result.getUpdateId();
            StarVo starVo = new StarVo();
            String key = prefix+updateId;
            setStarVoFromRedis(starVo, key);
            starVo.setStarDate(result.getStarDate());
            starVos.add(starVo);
        }
        return starVos;
    }

    /**
     * 从redis中取数据封装到starVo中
     * @param starVo
     * @param key
     */
    private void setStarVoFromRedis(StarVo starVo, String key) {
        if(redisService.hGet(key,"vid")==null){
            starVo.setVid(-1);   //找不到就将vid设为-1
            return;
        }
        starVo.setVid((Integer) redisService.hGet(key,"vid"));
        starVo.setTitle((String) redisService.hGet(key,"title"));
        starVo.setPlayNum((Integer) redisService.hGet(key,"playNum"));
        starVo.setStarNum((Integer) redisService.hGet(key,"starNum"));
        starVo.setUpName((String) redisService.hGet(key,"upName"));
        LocalDateTime uploadTime = LocalDateTime.parse((String) redisService.hGet(key, "uploadTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        starVo.setUploadTime(uploadTime);
    }


}
