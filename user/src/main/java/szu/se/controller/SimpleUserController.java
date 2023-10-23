package szu.se.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.common.api.CommonResult;
import szu.se.dao.SimpleUser;
import szu.se.service.SimpleUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.se.controller
 * @Author: Origami
 * @Date: 2023/10/2 10:18
 */
@RestController
@RequestMapping("/user")
public class SimpleUserController {

    @Resource
    private SimpleUserService simpleUserService;


    @GetMapping("/list")
    public CommonResult<List<SimpleUser>> getRes() {
        return CommonResult.success(simpleUserService.getSimpleUserList());
    }
}
