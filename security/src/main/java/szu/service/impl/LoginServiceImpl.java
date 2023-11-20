package szu.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import szu.common.api.CommonResult;
import szu.common.model.GlobalPermissionMap;
import szu.common.util.ShaUtil;
import szu.dao.LoginDao;
import szu.dao.PermissionDao;
import szu.dto.AuthDto;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;
import szu.model.User;
import szu.service.LoginService;
import szu.service.MailService;
import szu.util.PinUtil;
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

    @Resource
    private MailService mailService;
    @Value("${redis.user_prefix}")
    private String REDIS_USER_PREFIX;
    @Value("${redis.register_pin}")
    private String REDIS_REGISTER_PIN;

    @Override
    public CommonResult<String> register(RegisterDto registerDto) {
        String name = registerDto.getName();
        if (name == null || "".equals(name)) {
            return CommonResult.failed("用户昵称不能为空！");
        }
        int type = registerDto.getType();
        switch (type) {
            case 0: {
                break;
            }
            case 1: {
                System.out.println("手机号+密码注冊");
                String phone = registerDto.getPhone();
                if (phone == null || "".equals(phone)) {
                    return CommonResult.failed("手机号不能为空！");
                }
                String pswd = registerDto.getPswd();
                if (pswd == null || "".equals(pswd)) {
                    return CommonResult.failed("密码不能为空！");
                }
                // 创建用户
                loginDao.registerByPhone(name, phone, pswd);
                break;
            }
            case 2: {
                System.out.println("邮箱+验证码注册");
                String email = registerDto.getEmail();
                if (email == null || "".equals(email)) {
                    return CommonResult.failed("邮箱不能为空！");
                }
                String pin = registerDto.getPin();
                if (pin == null || "".equals(pin)) {
                    return CommonResult.failed("验证码不能为空！");
                }
                if (!checkPin(email, pin)) {
                    return CommonResult.failed("验证码错误！");
                }
                // 创建用户
                loginDao.registerByEmail(name, email);
                break;
            }
            default:
                System.out.println("不匹配的类型");
                break;
        }
        return CommonResult.success("注冊成功！");
    }

    @Override
    public String login(LoginDto loginDto) {
        String phone = loginDto.getPhone();
        String pswd = loginDto.getPswd();
        String encryptedPswd = ShaUtil.encode(pswd);
        System.out.println("encryptedPswd = " + encryptedPswd);
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

    @Override
    public User getCurrentUser(String token) {
        User user = (User) redisTemplate.opsForValue().get(REDIS_USER_PREFIX + token);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public boolean checkPin(String suffix, String pin) {
        String p = (String) redisTemplate.opsForValue().get(REDIS_REGISTER_PIN + suffix);
        return Objects.equals(p, pin);
    }

    @Override
    public CommonResult<String> getPin(AuthDto authDto) {
        String auth = authDto.getAuth();
        if (auth == null || "".equals(auth)) {
            return CommonResult.failed("手机号或邮箱不能为空！");
        }
        int type = authDto.getType();
        String pin = PinUtil.generatePin();
        System.out.println("pin = " + pin);
        redisTemplate.opsForValue().set(REDIS_REGISTER_PIN + auth, pin);
        // 验证码2min内有效
        redisTemplate.expire(REDIS_REGISTER_PIN + auth, 2, TimeUnit.MINUTES);
        switch (type) {
            case 0: {
                // 邮箱
                mailService.sendTextMailMessage(auth, pin);
                break;
            }
            case 1: {
                // 手机号

                break;
            }
            default:
                break;
        }

        return CommonResult.success("发送验证码成功！");
    }

    @Override
    public boolean logout(String token) {
        if (redisTemplate.opsForValue().get(REDIS_USER_PREFIX + token) != null) {
            redisTemplate.delete(REDIS_USER_PREFIX + token);
            return true;
        }
        return false;
    }


}
