package szu.validator;

import java.lang.annotation.*;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common.validator
 * @Author: Origami
 * @Date: 2023/10/30 19:39
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginValidator {
    boolean validated() default true;
}