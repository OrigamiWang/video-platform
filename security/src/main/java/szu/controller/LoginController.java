package szu.controller;

import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.dto.AuthDto;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;
import szu.service.LoginService;
import szu.validator.LoginValidator;
import szu.validator.PermissionValidator;

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


    // validated = false: 不用校验
    @PostMapping("/register")
    public CommonResult<String> register(@RequestBody RegisterDto registerDto) {
        return loginService.register(registerDto);
    }

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody LoginDto loginDto) {
        String uuid = loginService.login(loginDto);
        if (uuid.length() == 0) {
            return CommonResult.failed("账号或密码错误！");
        }
        return CommonResult.success(uuid);
    }

    @PostMapping("/pin")
    public CommonResult<String> getPin(@RequestBody AuthDto authDto) {
        return loginService.getPin(authDto);
    }

    @GetMapping("/p1")
    @PermissionValidator(pid = 1)
    @LoginValidator
    public CommonResult<String> p1() {
        return CommonResult.success("when the pid is 1");
    }

    @GetMapping("/p6")
    @PermissionValidator(pid = 6)
    @LoginValidator
    public CommonResult<String> p6() {
        return CommonResult.success("when the pid is 6");
    }


}
