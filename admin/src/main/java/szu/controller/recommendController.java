package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
import szu.service.RecommendService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: recommendController
 * @Description: 推荐模块
 * @Version 1.0
 * @Date: 2023-11-29 19:41
 * @Auther: UserXin
 */
@RestController
@Api(tags = "推荐模块")
@Slf4j
@RequestMapping("/recommend")
public class recommendController {

    @Resource
    private RecommendService recommendService;

    /**
     * 增加得分
     * @param uid 当前登录的用户id
     * @param updateId 动态id
     * @param score 得分：（观看：1 点赞：2 投币：3 收藏：4）
     * @return
     */
    @PostMapping("/plusScore/{uid}/{updateId}/{score}")
    @ApiOperation("增加得分")
    public CommonResult<ResultCode> plusScore(@PathVariable("uid") @ApiParam("当前登录的用户id") Integer uid,
                                              @PathVariable("updateId") @ApiParam("动态id") Integer updateId,
                                              @PathVariable("score") @ApiParam("得分：(观看：1 点赞：2 投币：3 收藏：4)") Integer score){
        log.info("增加得分：uid：{}，updateId：{}，score：{}",uid,updateId,score);
        recommendService.plusScore(uid,updateId,score);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @GetMapping("/getRecommend/{uid}")
    public CommonResult<List<Integer>> getRecommend(@PathVariable("uid") Integer uid) throws IOException, TasteException {
        List<Integer> integers = recommendService.recommendUpdateList(uid);
        return CommonResult.success(integers);
    }

}
