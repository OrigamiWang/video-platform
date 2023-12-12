package szu.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import szu.dao.UserInfoDao;
import szu.model.User;
import szu.model.UserDetail;
import szu.vo.UserInfo;
import szu.model.UserStatistics;
import szu.service.UserInfoService;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private RedisTemplate<String, UserInfo> redisTemplate;

    private final String USER_INFO_PREFIX = "userinfo:";
    @Override
    public UserInfo getUserInfoByUid(int uid) {
        UserInfo userInfo = redisTemplate.opsForValue().get(USER_INFO_PREFIX + uid);
        if (userInfo == null) {
            userInfo = new UserInfo();
            // 从mysql中获取
            User user = userInfoDao.getUserById(uid);//id、昵称、等级
            UserDetail userDetail = userInfoDao.getUserDetailById(uid);//性别、ip、自我介绍
            UserStatistics userStatistics = userInfoDao.getUserStatisticsById(uid);//粉丝、关注、点赞
            BeanUtils.copyProperties(user, userInfo);
            BeanUtils.copyProperties(userDetail, userInfo);
            BeanUtils.copyProperties(userStatistics, userInfo);
            // 保存到redis中
            redisTemplate.opsForValue().set(USER_INFO_PREFIX + uid, userInfo);
        }
        return userInfo;
    }

    @Override
    public Integer getUidByName(String upName) {
        return null;
    }
}
