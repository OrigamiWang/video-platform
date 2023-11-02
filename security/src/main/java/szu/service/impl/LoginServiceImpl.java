package szu.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import szu.common.model.GlobalPermissionMap;
import szu.common.util.ShaUtil;
import szu.dao.LoginDao;
import szu.dao.PermissionDao;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;
import szu.model.User;
import szu.service.LoginService;
import szu.util.UuidUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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

    @Resource
    private PermissionDao permissionDao;

    @Value("${redis.user_prefix}")
    private String REDIS_USER_PREFIX;

    @Override
    public void register(RegisterDto registerDto) {
        // encrypt the pswd
        String pswd = registerDto.getPswd();
        String encryptedPswd = ShaUtil.encode(pswd);
        System.out.println("encryptedPswd = " + encryptedPswd);
        loginDao.register(registerDto.getName(), registerDto.getPhone(), encryptedPswd);
    }

    @Override
    public String login(LoginDto loginDto) {
        String phone = loginDto.getPhone();
        String pswd = loginDto.getPswd();
        String encryptedPswd = ShaUtil.encode(pswd);
        User user = loginDao.getUser(phone);
        if (user == null || !Objects.equals(encryptedPswd, user.getPassword())) {
            return "";
        }
        // set permission
        Map<Integer, Integer> map = GlobalPermissionMap.getInstance();
        int uid = user.getId();
        List<Integer> permissionList = permissionDao.getUserPermission(uid);
        for (Integer p : permissionList) {
            map.put(p, 0);
        }
        // use uuid as token
        String uuid = UuidUtil.getUuid();
        // save the user in redis to keep logining
        redisTemplate.opsForValue().set(REDIS_USER_PREFIX + uuid, user);
        redisTemplate.expire(REDIS_USER_PREFIX + uuid, 30, TimeUnit.MINUTES);
        return uuid;
    }

}
