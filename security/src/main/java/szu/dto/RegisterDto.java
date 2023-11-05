package szu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Null;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dto
 * @Author: Origami
 * @Date: 2023/10/29 10:11
 */
@Data
public class RegisterDto {

    private String name;
    private String username;
    private String email;
    private String phone;
    private String pswd;
    // 表示登录的方式（账号密码、手机号验证码、二维码...）
    private int type;
    private String pin;

}
