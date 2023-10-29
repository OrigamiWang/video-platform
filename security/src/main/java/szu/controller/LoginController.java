package szu.controller;

import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.dto.RegisterDto;
import szu.service.LoginService;

import javax.annotation.Resource;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.controller
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

    @GetMapping("test")
    public CommonResult<String> test() {
        return CommonResult.success("hello");
    }
}
