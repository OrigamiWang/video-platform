package szu.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import szu.dao.UserInfoDao;
import szu.model.User;
import szu.model.UserInfo;
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
            // 从mysql中获取
            User user = userInfoDao.getUserById(uid);
            userInfo = new UserInfo();
            userInfo.setId(uid);
            userInfo.setName(user.getName());
            userInfo.setLevel(user.getLevel());
            // 保存到redis中
            redisTemplate.opsForValue().set(USER_INFO_PREFIX + uid, userInfo);
        }
        return userInfo;
    }
}
