package szu.dto;

import lombok.Data;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dto
 * @Author: Origami
 * @Date: 2023/11/5 18:52
 */
@Data
public class AuthDto {
    private String auth;
    // 0: 邮箱
    // 1: 手机号
    private int type;
}
