package szu.service;

import szu.common.api.CommonResult;


/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/11/4 17:42
 */
public interface MailService {
    CommonResult<String> sendTextMailMessage(String to, String pin);
}
