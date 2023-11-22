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

    private String pswd;
}
