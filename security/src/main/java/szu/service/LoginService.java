package szu.service;

import szu.common.api.CommonResult;
import szu.dto.AuthDto;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;
import szu.model.User;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/10/29 10:08
 */
public interface LoginService {
    CommonResult<String> register(RegisterDto registerDto);

    String login(LoginDto loginDto);

    /**
     * 获取当前登录用户信息
     * @param token 登录凭证
     * @return 当前登录用户信息
     */
    User getCurrentUser(String token);

    boolean checkPin(String phone, String pin);

    CommonResult<String> getPin(AuthDto authDto);

    boolean logout(String token);

}
