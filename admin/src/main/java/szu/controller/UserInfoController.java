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
import szu.common.api.ListResult;
import szu.service.VideoService;
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
    @Resource
    private VideoService videoService;

    @GetMapping("/get-by-uid")
    @ApiOperation("根据uid获取用户基础信息")
    public CommonResult<UserInfo> getUserInfoByUid(@RequestParam Integer uid) {
        UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
        return userInfo != null ? CommonResult.success(userInfo) : CommonResult.failed("获取用户信息失败");
    }

    @GetMapping("/list-video-by-uid")
    @ApiOperation("根据uid获取用户的投稿列表，total表示该用户的所有投稿量")
    public CommonResult<ListResult<VideoVo>> getUserVideo(@RequestParam @ApiParam("指定uid") Integer uid,
                                              @RequestParam(defaultValue = "0") @ApiParam("指定排列方式，0最新、1最多播放、2最多收藏，默认为0") Integer sort,
                                              @RequestParam(defaultValue = "1") @ApiParam("第几页，默认1") Integer page,
                                              @RequestParam(defaultValue = "10") @ApiParam("每页数量，默认10") Integer size
    ){
        ListResult<VideoVo> res = videoService.getVideoById(uid, sort, page, size);
        if(res == null) return CommonResult.failed("用户不存在");
        return CommonResult.success(res);
    }
}
