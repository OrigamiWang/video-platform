package szu.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import szu.dao.StarContentDao;
import szu.dao.StarDao;
import szu.model.Star;
import szu.model.StarContent;
import szu.service.StarService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private StarContentDao starContentDao;

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
        if(starDao.addStar(uid, starName)>0){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 收藏视频
     * @param sids 收藏夹id集合
     * @param vid 要收藏的视频id
     * @return
     */
    @Override
    @Transactional //添加事务
    public boolean starVideo(List<Integer> sids, Integer vid) {
        if(vid<0 || sids==null || sids.size()==0) throw new IllegalArgumentException();
        int successNum = 0;
        for (Integer sid : sids) {
            if(starDao.updateStarNumById(sid)>0){ //设置收藏夹收藏数量加一
                //添加视频在收藏夹内
                StarContent starContent = new StarContent();
                starContent.setSid(sid);
                starContent.setVid(vid);
                starContent.setStarDate(LocalDateTime.now());
                if(starContentDao.starVideo(starContent)>0){
                    successNum++;
                }
            }
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
        return starDao.listStarByUid(uid);
    }



}
