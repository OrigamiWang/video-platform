package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.dto.AuthDto;
import szu.dto.LoginDto;
import szu.dto.RegisterDto;
import szu.model.User;
import szu.service.LoginService;
import szu.service.UserService;
import szu.util.AuthUtil;
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
@Api(tags = "LoginController")
@RequestMapping("/scy")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private UserService userService;


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

    @GetMapping("/current")
    @ApiOperation("获取当前登录用户信息")
    @LoginValidator
    public CommonResult<User> getCurrentUser(@RequestHeader String token) {
        return CommonResult.success(loginService.getCurrentUser(token));
    }

    @PostMapping("/update")
    @ApiOperation("修改用户信息")
    @LoginValidator
    public CommonResult<Boolean> updateUsername(@RequestBody User user) {
        User newUser = new User();
        newUser.setId(AuthUtil.getCurrentUser().map(User::getId).orElse(0));
        if (user.getName() != null) {
            newUser.setName(user.getName());
        }
        if (user.getPassword() != null) {
            newUser.setPassword(user.getPassword());
        }
        userService.updateUser(newUser);
        return CommonResult.success(true);
    }

    @PostMapping("/pin")
    public CommonResult<String> getPin(@RequestBody AuthDto authDto) {
        return loginService.getPin(authDto);
    }

    @GetMapping("/logout")
    @LoginValidator
    public CommonResult<String> logout(@RequestHeader String token) {
        System.out.println("token = " + token);
        loginService.logout(token);
        return CommonResult.success("登出");
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