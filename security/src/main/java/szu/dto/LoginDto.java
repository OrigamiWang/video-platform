package szu.dto;

import lombok.Data;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dto
 * @Author: Origami
 * @Date: 2023/10/30 19:16
 */
@Data
public class LoginDto {
    private String phone;
    private String email;
    private String pswd;
    private String pin;
    // 0: 手机号+密码
    // 1: 邮箱+验证码
    private int type;
}
