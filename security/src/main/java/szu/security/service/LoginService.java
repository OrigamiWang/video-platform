package szu.security.service;

import szu.security.dto.RegisterDto;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.security.service
 * @Author: Origami
 * @Date: 2023/10/29 10:08
 */
public interface LoginService {
    void register(RegisterDto registerDto);
}
