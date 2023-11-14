package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
import szu.model.Permission;
import szu.model.Role;
import szu.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "RoleController")
@Tag(name = "RoleController", description = "权限管理")
@RequestMapping("/mg/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation("添加角色信息")
    public CommonResult<ResultCode> addRole(@RequestBody @ApiParam("传入需要添加的角色") Role role){
        boolean flag = roleService.addRole(role);
        if(!flag) return CommonResult.failed("角色已存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }
    @DeleteMapping("/delete")
    @ApiOperation("删除角色信息")
    public CommonResult<ResultCode> deleteRole(@RequestParam @ApiParam("角色名称") String roleName){
        boolean flag = roleService.deleteRole(roleName);
        if(!flag) return CommonResult.failed("角色不存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @PostMapping("/update")
    @ApiOperation("修改oldRole为newRole")
    public CommonResult<ResultCode> updateRole(@RequestParam @ApiParam("旧角色名称") String oldRole,
                                               @RequestParam @ApiParam("新角色名称") String newRole){
        boolean flag = roleService.updateRole(oldRole, newRole);
        if(!flag) return CommonResult.failed("角色不存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @GetMapping("/list")
    @ApiOperation("查询所有角色信息")
    public CommonResult<List<Role>> getRoleList(){
        List<Role> roles = roleService.listRole();
        return CommonResult.success(roles);
    }


//    @ApiOperation("为角色增加权限（一个或多个")
//    @PostMapping("/addrolepermission/{rid}")
//    public CommonResult<ResultCode> addRolePermission(@RequestParam @ApiParam("以逗号分隔的pid字符串形式提交，如’1,2,3‘、’1‘") String permissions,
//                                                      @PathVariable @ApiParam("角色id")Integer rid
//    ){
//        roleService.addRolePermission(permissions);
//        return CommonResult.success(ResultCode.SUCCESS);
//    }
//
//    @ApiOperation("为角色删除权限（一个或多个")
//    @PostMapping("/deleterolepermission")
//    public CommonResult<ResultCode> addRolePermission(@RequestParam @ApiParam("以逗号分隔的pid字符串形式提交如’1,2,3‘、’1‘") String permissions){
//        roleService.deleteRolePermission(permissions);
//        return CommonResult.success(ResultCode.SUCCESS);
//    }


    @PostMapping("/permission/add")
    @ApiOperation("增加权限")
    public CommonResult<ResultCode> addPermission(@RequestParam @ApiParam("权限名称") String permission){
        boolean flag = roleService.addPermission(permission);
        if(!flag) return CommonResult.failed("权限已存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @DeleteMapping("/permission/delete")
    @ApiOperation("删除权限")
    public CommonResult<ResultCode> deletePermission(@RequestParam @ApiParam("权限名称") String permission){
        boolean flag = roleService.deletePermission(permission);
        if(!flag) return CommonResult.failed("权限不存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @GetMapping("/permission/list")
    @ApiOperation("查询所有权限信息")
    public CommonResult<List<Permission>> getPermissionList(){
        List<Permission> pers = roleService.listPermission();
        return CommonResult.success(pers);
    }



}
