package szu.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import szu.dao.LoginDao;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;
import szu.model.User;
import szu.service.LoginService;
import szu.util.UuidUtil;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service.impl
 * @Author: Origami
 * @Date: 2023/10/29 10:09
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private LoginDao loginDao;

    @Value("${redis.user_prefix}")
    private String REDIS_USER_PREFIX;

    @Override
    public void register(RegisterDto registerDto) {
        // encrypt the pswd
        String pswd = registerDto.getPswd();
        String encryptedPswd = pswd;
        loginDao.register(registerDto.getName(), registerDto.getPhone(), encryptedPswd);
    }

    @Override
    public String login(LoginDto loginDto) {
        String phone = loginDto.getPhone();
        String pswd = loginDto.getPswd();
        User user = loginDao.getUser(phone);
        if (user == null || !Objects.equals(pswd, user.getPassword())) {
            return "";
        }
        // use uuid as token
        String uuid = UuidUtil.getUuid();
        // save the user in redis to keep logining
        redisTemplate.opsForValue().set(REDIS_USER_PREFIX + uuid, user);
        redisTemplate.expire(REDIS_USER_PREFIX + uuid, 30, TimeUnit.MINUTES);
        return uuid;
    }

}
