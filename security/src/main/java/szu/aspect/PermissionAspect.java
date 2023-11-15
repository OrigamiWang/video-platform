package szu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import szu.common.api.CommonResult;
import szu.common.model.GlobalPermissionMap;
import szu.validator.PermissionValidator;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.aspect
 * @Author: Origami
 * @Date: 2023/11/2 21:39
 */
@Component
@Aspect
public class PermissionAspect {
    @Pointcut(value = "@annotation(szu.validator.PermissionValidator) || @within(szu.validator.PermissionValidator)")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinpoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        PermissionValidator permissionValidator = method.getAnnotation(PermissionValidator.class);
        if (permissionValidator == null || !permissionValidator.validated()) {
            // 放行
            return joinpoint.proceed(joinpoint.getArgs());
        }
        int pid = permissionValidator.pid();
        Map<Integer, Integer> map = GlobalPermissionMap.getInstance();
        if (map.containsKey(pid)) {
            // 有权限
            return joinpoint.proceed(joinpoint.getArgs());
        }
        // 无权限
        return CommonResult.failed("无权限");
    }
}
