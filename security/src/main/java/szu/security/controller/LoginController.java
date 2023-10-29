package szu.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.common.api.CommonResult;
import szu.security.dto.RegisterDto;
import szu.security.service.LoginService;

import javax.annotation.Resource;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.security.controller
 * @Author: Origami
 * @Date: 2023/10/29 10:13
 */

@RestController
@RequestMapping("/scy")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("register")
    public CommonResult<String> register(@RequestBody RegisterDto registerDto) {
        loginService.register(registerDto);
        return CommonResult.success("success");
    }
}
