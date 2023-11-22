package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import szu.common.api.CommonResult;
import szu.vo.UserInfo;
import szu.service.UserInfoService;

import javax.annotation.Resource;

@RestController
@Api(tags = "UserInfoController")
@Tag(name = "UserInfoController", description = "用于获取用户信息")
@RequestMapping("/user-info")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/get-by-uid")
    public CommonResult<UserInfo> getUserInfoByUid(@RequestParam Integer uid) {
        UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
        return userInfo != null ? CommonResult.success(userInfo) : CommonResult.failed("获取用户信息失败");
    }
}
