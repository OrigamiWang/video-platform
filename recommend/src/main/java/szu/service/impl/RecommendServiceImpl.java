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
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import szu.common.service.RedisService;
import szu.dao.UpdateAndVideoDao;
import szu.model.PartitionScore;
import szu.model.UserPreferences;
import szu.model.VideoScore;
import szu.service.RecommendService;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    private UpdateAndVideoDao updateAndVideoDao;

    /**
     * 增加得分
     * @param uid 当前登录的用户id
     * @param updateId 动态id
     * @param score 得分：(观看：+1 点赞：+2 投币：+3 收藏：+4)
     * @return
     */
    @Override
    public void plusScore(Integer uid, Integer updateId, Float score) {
        Query query = Query.query(Criteria.where("uid").is(uid));
        UserPreferences userPreferences = mongoTemplate.findOne(query, UserPreferences.class);
        if(userPreferences==null){
            //没有uid就先插入
            mongoTemplate.save(new UserPreferences(ObjectId.get().toString(),uid,new ArrayList<>(),new ArrayList<>()));
        }
        //更新视频得分
        //获取分区id
        Integer pid = updateAndVideoDao.getPidByUpdateId(updateId);
        Query query2 = Query.query(Criteria.where("uid").is(uid).and("videoScores.updateId").is(updateId));
        UserPreferences userPreferences1 = mongoTemplate.findOne(query2, UserPreferences.class);
        Update update = new Update();
        if(userPreferences1==null){
            //没看过这个视频
            update.addToSet("videoScores", new VideoScore(ObjectId.get().toString(),updateId,score));
            update.addToSet("partitionScores", new PartitionScore(ObjectId.get().toString(),pid,1f));
            mongoTemplate.upsert(query, update, UserPreferences.class);
        }else{
            update.inc("videoScores.$.score", score);
            update.inc("partitionScores.$.score", 1);
            mongoTemplate.upsert(query2, update, UserPreferences.class);
        }
    }

    @Override
    public List<Integer> recommendUpdateList(Integer uid) throws TasteException {

        GenericItemBasedRecommender recommender = getGenericItemBasedRecommender();
        //推荐50个视频
        List<RecommendedItem> recommend = recommender.recommend(uid, 50);
        return getResult(uid, recommend);
    }

    @Override
    public List<Integer> recommendUpdateListByUpdateId(Integer uid, Integer updateId) throws TasteException {
        GenericItemBasedRecommender recommender = getGenericItemBasedRecommender();
        //推荐50个视频
        List<RecommendedItem> recommend = recommender.recommendedBecause(uid,updateId, 50);
        return getResult(uid, recommend);
    }

    /**
     * 根据分区进行筛选
     * @param uid
     * @param recommend
     * @return
     */
    @NotNull
    private List<Integer> getResult(Integer uid, List<RecommendedItem> recommend) {
        List<Integer> recommendUpdateId = new ArrayList<>();
        for (RecommendedItem recommendedItem : recommend) {
            System.out.println(recommendedItem);
            recommendUpdateId.add((int) recommendedItem.getItemID());
        }
        Query query = Query.query(Criteria.where("uid").is(uid));
        UserPreferences user = mongoTemplate.findOne(query, UserPreferences.class);
        assert user != null;
        List<PartitionScore> partitionScores = user.getPartitionScores();
        //按分区得分排序
        partitionScores.sort((o1, o2) -> (int) (o1.getScore()-o2.getScore()));

        List<Integer> result = new ArrayList<>();
        //如果历史数据不足，推荐的视频为空则返回得分最高的前五个pid(以-1开头)
        if(recommend.isEmpty()){
            result.add(-1);
            for (int i = 0; i < 5; i++) {
                result.add(partitionScores.get(i).getPid());
            }
            return result;
        }
        //取出在分区得分前五内的updateId
        for (Integer u : recommendUpdateId) {
            Integer pid = updateAndVideoDao.getPidByUpdateId(u);
            for (int i = 0; i < 5; i++) {
                if(partitionScores.get(i).getPid()==pid){
                    result.add(u);
                    break;
                }
            }
        }
        return result;
    }


    /**
     * 构建推荐器
     * @return
     * @throws TasteException
     */
    @NotNull
    private GenericItemBasedRecommender getGenericItemBasedRecommender() throws TasteException {
        List<UserPreferences> userPreferences = mongoTemplate.findAll(UserPreferences.class);
        // 创建一个新的 FastByIDMap 用于存储用户-项偏好 FastByIDMap：key: userId，value:<userId,<ItemId..>,<score..>>
        FastByIDMap<PreferenceArray> userData = new FastByIDMap<>();
        //遍历每个用户的喜好
        for (UserPreferences userPreference : userPreferences) {
            long userID = userPreference.getUid();
            List<VideoScore> videoScores = userPreference.getVideoScores();
            PreferenceArray preferenceArray = new GenericUserPreferenceArray(videoScores.size());
            preferenceArray.setUserID(0,userID);
            int i=0;
            for (VideoScore videoScore : videoScores) {
                preferenceArray.setItemID(i,videoScore.getUpdateId());
                preferenceArray.setValue(i,videoScore.getScore());
                i++;
            }
            userData.put(userID,preferenceArray);
        }
        // 创建 GenericDataModel，并传入 userData
        GenericDataModel dataModel = new GenericDataModel(userData);

        // 计算相似度
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        // 构建推荐器，使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        return recommender;
    }
}
