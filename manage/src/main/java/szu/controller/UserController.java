package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserStatistics;
import szu.service.UserService;
import szu.validator.LoginValidator;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.controller
 * @Author: Origami
 * @Date: 2023/10/28 15:29
 */
@RestController
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户管理")
@RequestMapping("/mg/user")
//@LoginValidator
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    @ApiOperation("获取所有用户（仅user表）")
    public CommonResult<Object> getAllUser() {
        List<User> userList = userService.getUserList();
        if(userList == null) return CommonResult.failed("用户列表为空");
        return CommonResult.success(userList);
    }

    @PostMapping("/add")
    @ApiOperation("新增用户，同时新增关联的默认detail、statistics、role信息")
    public CommonResult<ResultCode> addUser(@RequestBody@ApiParam("传递的用户对象json") User user){
        userService.insert(user);
        return CommonResult.success(ResultCode.SUCCESS);
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation("根据id删除用户，同时删除与之关联的detail、statistics、role信息")
    public CommonResult<String> deleteUserById(@PathVariable("id")@ApiParam("用户id") Integer id){
        boolean flag = userService.deleteById(id);
        if(!flag) return CommonResult.failed("用户不存在");
        return CommonResult.success("删除成功");
    }

    @PutMapping("/update")
    @ApiOperation("根据用户id修改用户（仅user）")
    public CommonResult<ResultCode> updateUser(@RequestBody@ApiParam("传递的用户对象json") User user){
        userService.updateUser(user);
        return CommonResult.success(ResultCode.SUCCESS);
    }


    @PostMapping("/addrole/{userId}/{roleId}")
    @ApiOperation("根据用户id、权限id增加权限")
    public CommonResult<ResultCode> addRoleById(@PathVariable@ApiParam("用户id") Integer userId, @PathVariable@ApiParam("角色id") Integer roleId){
        boolean flag = userService.addRoleById(userId, roleId);
        if(!flag) return CommonResult.failed("权限已拥有");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @PostMapping("/deleterole/{userId}/{roleId}")
    @ApiOperation("根据用户id。权限id删除权限")
    public CommonResult<ResultCode> deleteRoleById(@PathVariable@ApiParam("用户id") Integer userId, @PathVariable@ApiParam("角色id") Integer roleId){
        boolean flag = userService.deleteRoleById(userId, roleId);
        if(!flag) return CommonResult.failed("角色未拥有该权限");
        return CommonResult.success(ResultCode.SUCCESS);
    }


    @PutMapping("/updatestatistics")
    @ApiOperation("根据uid更新statistics表信息")
    public CommonResult<ResultCode> updateStatisticsById(@RequestBody@ApiParam("传递的UserStatistics信息") UserStatistics userStatistics){
        userService.updateUserStatistics(userStatistics);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @PutMapping("/updatedetail")
    @ApiOperation("根据uid更新detail表信息")
    public CommonResult<ResultCode> updateDetailById(@RequestBody@ApiParam("传递的UserDetail信息") UserDetail userDetail){
        userService.updateUserDetail(userDetail);
        return CommonResult.success(ResultCode.SUCCESS);
    }


}
