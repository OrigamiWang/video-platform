package szu.security.dto;

import lombok.*;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.security.dto
 * @Author: Origami
 * @Date: 2023/10/29 10:11
 */
@Data
public class RegisterDto {

    private String name;
    private String phone;
    private String pswd;

}
