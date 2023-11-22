package szu.service;

import szu.common.api.CommonResult;
import szu.dto.AuthDto;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/10/29 10:08
 */
public interface LoginService {
    CommonResult<String> register(RegisterDto registerDto);

    String login(LoginDto loginDto);

    boolean checkPin(String phone, String pin);

    CommonResult<String> getPin(AuthDto authDto);

    boolean logout(String token);
}
