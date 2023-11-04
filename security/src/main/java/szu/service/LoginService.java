package szu.service;

import szu.dto.LoginDto;
import szu.dto.RegisterDto;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/10/29 10:08
 */
public interface LoginService {
    void register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
