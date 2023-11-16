package szu.aspect;

import io.micrometer.core.instrument.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import szu.common.api.CommonResult;
import szu.model.User;
import szu.validator.LoginValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common.aspect
 * @Author: Origami
 * @Date: 2023/10/30 19:40
 */
@Component
@Aspect
public class LoginAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.user_prefix}")
    private String REDIS_USER_PREFIX;

    @Pointcut(value = "@annotation(szu.validator.LoginValidator) || @within(szu.validator.LoginValidator)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinpoint) throws Throwable {
        // 获取方法方法上的LoginValidator注解
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        LoginValidator loginValidator = method.getAnnotation(LoginValidator.class);
        // 如果有，并且值为false，则不校验
        if (loginValidator == null || !loginValidator.validated()) {
            return joinpoint.proceed(joinpoint.getArgs());
        }
        // 正常校验 获取request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || requestAttributes.getResponse() == null) {
            // 如果不是从前段过来的，没有request，则直接放行
            return joinpoint.proceed(joinpoint.getArgs());
        }
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return CommonResult.failed("no token");
        }
        // 从redis中拿token对应user
        User user = (User) redisTemplate.opsForValue().get(REDIS_USER_PREFIX + token);
        if (user == null) {
            return CommonResult.failed("user not login");
        }
        // token续期
        redisTemplate.expire(REDIS_USER_PREFIX + token, 30, TimeUnit.MINUTES);
        // 放行
        return joinpoint.proceed(joinpoint.getArgs());
    }

}
