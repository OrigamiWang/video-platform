package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
import szu.model.User;
import szu.service.UserService;
import szu.validator.LoginValidator;
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
@LoginValidator
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user")
    @ApiOperation("获取所有用户")
    public CommonResult<List<User>> getAllUser() {
        List<User> userList = userService.getUserList();
        return CommonResult.success(userList);
    }

    @PostMapping("/user/add")
    @ApiOperation("新增用户")
    public CommonResult<ResultCode> addUser(@RequestBody User user){
        userService.insert(user);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @DeleteMapping("/user/delete/{id}")
    @ApiOperation("根据id删除用户")
    public CommonResult<ResultCode> deleteUserById(@PathVariable Integer id){
        userService.deleteById(id);
        return CommonResult.success(ResultCode.SUCCESS);
    }


    @PutMapping("/user/update")
    @ApiOperation("根据用户id修改用户")
    public CommonResult<ResultCode> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return CommonResult.success(ResultCode.SUCCESS);
    }




}
