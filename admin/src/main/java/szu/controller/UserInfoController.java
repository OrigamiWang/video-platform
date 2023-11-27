package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import szu.common.api.CommonResult;
import szu.vo.UserInfo;
import szu.service.UserInfoService;
import szu.vo.VideoVo;

import javax.annotation.Resource;

@RestController
@Api(tags = "UserInfoController")
@Tag(name = "UserInfoController", description = "用于获取用户信息")
@RequestMapping("/user-info")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/get-by-uid")
    @ApiOperation("根据uid获取用户基础信息")
    public CommonResult<UserInfo> getUserInfoByUid(@RequestParam Integer uid) {
        UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
        return userInfo != null ? CommonResult.success(userInfo) : CommonResult.failed("获取用户信息失败");
    }

    @GetMapping("/list-video")
    @ApiOperation("根据uid获取用户的投稿")
    public CommonResult<VideoVo> getUserVideo(@RequestParam @ApiParam("指定uid") Integer uid,
                                              @RequestParam(defaultValue = "0") @ApiParam("指定排列方式，0最新、1最多播放、2最多收藏，默认为0") Integer sort){

        return null;
    }
}
