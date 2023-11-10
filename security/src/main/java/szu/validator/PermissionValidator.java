package szu.validator;

import java.lang.annotation.*;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.validator
 * @Author: Origami
 * @Date: 2023/11/2 21:38
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionValidator {
    boolean validated() default true;

    // 需要的权限id
    int pid();
}
