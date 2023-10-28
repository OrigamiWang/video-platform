package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.common.api.CommonResult;
import szu.model.User;
import szu.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.controller
 * @Author: Origami
 * @Date: 2023/10/28 15:29
 */
@RestController
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户管理")
@RequestMapping("/mg")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/user")
    @ApiOperation("获取所有用户")
    public CommonResult<List<User>> getAllUser() {
        List<User> userList = userService.getUserList();
        return CommonResult.success(userList);
    }
}
