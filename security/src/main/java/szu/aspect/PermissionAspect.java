package szu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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


        // 放行
        return joinpoint.proceed(joinpoint.getArgs());
    }
}
