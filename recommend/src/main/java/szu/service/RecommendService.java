package szu.service;




import org.apache.mahout.cf.taste.common.TasteException;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: recommendService
 * @Description: 推荐模块service层
 * @Version 1.0
 * @Date: 2023-11-29 19:47
 * @Auther: UserXin
 */
public interface RecommendService {
    /**
     * 增加得分
     * @param uid 当前登录的用户id
     * @param updateId 动态id
     * @param score 得分：（观看：1 点赞：2 投币：3 收藏：4）
     * @return
     */
    void plusScore(Integer uid, Integer updateId, Integer score);

    /**
     * 推荐视频
     * @return
     */
    List<Integer> recommendUpdateList(Integer uid) throws IOException, TasteException;

    /**
     * 根据当前观看的视频推荐新的
     * @param updateId
     * @return
     */
    List<Integer> recommendUpdateListByUpdateId(Integer updateId);

}
