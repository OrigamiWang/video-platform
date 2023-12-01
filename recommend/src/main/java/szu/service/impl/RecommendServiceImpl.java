package szu.service.impl;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import szu.common.service.RedisService;
import szu.model.PartitionScore;
import szu.model.VideoScore;
import szu.service.RecommendService;
import szu.service.RedisWithMysql;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: recommendServiceImpl
 * @Description: 推荐模块service实现类
 * @Version 1.0
 * @Date: 2023-11-29 19:47
 * @Auther: UserXin
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisWithMysql redisWithMysql;

    /**
     * 增加得分
     * @param uid 当前登录的用户id
     * @param updateId 动态id
     * @param score 得分：(观看：1 点赞：2 投币：3 收藏：4)
     * @return
     */
    @Override
    public void plusScore(Integer uid, Integer updateId, Integer score) {
        Query query = Query.query(Criteria.where("uid").is(uid));
        //更新视频得分
        Update update = new Update();
        update.set("uid",uid);
        update.set("updateId",updateId);
        update.set("score",score);
        mongoTemplate.upsert(query,update, VideoScore.class);

        //更新分区得分
        Integer pid = null;
        String key = "updates:videoUpdate:"+updateId;
        if(redisService.hGet(key,"vid")==null){
            //缓存未命中
            if(redisWithMysql.updateRedisByUpdateId(updateId)){  //更新缓存
                pid = (Integer) redisService.hGet(key,"pid");
            }
        }else{
            pid = (Integer) redisService.hGet(key,"pid");
        }
        update = new Update();
        update.set("uid",uid);
        update.set("pid",pid);
        update.inc("score");
        mongoTemplate.upsert(query,update, PartitionScore.class);
    }

    @Override
    public List<Integer> recommendUpdateList(Integer uid) throws TasteException {

        List<VideoScore> videoScores = mongoTemplate.findAll(VideoScore.class);
        List<Preference> preferences = new ArrayList<>();
        for (VideoScore videoScore : videoScores) {
            long userID = videoScore.getUid();
            long itemID = videoScore.getUpdateId();
            float rating =videoScore.getScore();
            Preference preference = new GenericPreference(userID, itemID, rating);
            preferences.add(preference);
        }
        // 创建一个新的 FastByIDMap 用于存储用户-项偏好
        FastByIDMap<PreferenceArray> userData = new FastByIDMap<>();

        // 遍历 preferences，并将每个偏好添加到 userData 中
        for (Preference preference : preferences) {
            long userID = preference.getUserID();
            PreferenceArray userPreferences = userData.get(userID);

            if (userPreferences == null) {
                userPreferences = new GenericUserPreferenceArray(1);
                userPreferences.setUserID(0, userID);
                userData.put(userID, userPreferences);
            }

            // 添加偏好到用户的 PreferenceArray 中
            int length = userPreferences.length();
            userPreferences.setItemID(length, preference.getItemID());
            userPreferences.setValue(length, preference.getValue());
        }

        // 创建 GenericDataModel，并传入 userData
        GenericDataModel dataModel = new GenericDataModel(userData);

        // 计算相似度
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        // 构建推荐器，使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

        List<RecommendedItem> recommend = recommender.recommend(uid, 50);
        List<Integer> results = new ArrayList<>();

        for (RecommendedItem recommendedItem : recommend) {
            System.out.println(recommendedItem);
            results.add((int) recommendedItem.getItemID());
        }

        return results;
    }

    @Override
    public List<Integer> recommendUpdateListByUpdateId(Integer updateId) {
        return null;
    }
}
