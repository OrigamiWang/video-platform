package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
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
    public CommonResult<ResultCode> addRole(String roleName){
        boolean flag = roleService.addRole(roleName);
        if(!flag) return CommonResult.failed("角色已存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }
    @DeleteMapping("/delete")
    @ApiOperation("删除角色信息")
    public CommonResult<ResultCode> deleteRole(String roleName){
        boolean flag = roleService.deleteRole(roleName);
        if(!flag) return CommonResult.failed("角色不存在");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @PostMapping("/update")
    @ApiOperation("修改oldRole为newRole")
    public CommonResult<ResultCode> updateRole(String oldRole, String newRole){
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


}
