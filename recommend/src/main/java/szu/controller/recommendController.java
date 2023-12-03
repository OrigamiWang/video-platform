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
     * @param score 得分：(观看：+1 ,点赞：+2 ,投币：+3 ,收藏：+4)
     * @return
     */
    @PostMapping("/plusScore/{uid}/{updateId}/{score}")
    @ApiOperation("增加得分")
    public CommonResult<ResultCode> plusScore(@PathVariable("uid") @ApiParam("当前登录的用户id") Integer uid,
                                              @PathVariable("updateId") @ApiParam("动态id") Integer updateId,
                                              @PathVariable("score") @ApiParam("得分：(观看：+1 ,点赞：+2 ,投币：+3 ,收藏：+4)") Float score){
        log.info("增加得分：uid：{}，updateId：{}，score：{}",uid,updateId,score);
        recommendService.plusScore(uid,updateId,score);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @GetMapping("/getRecommend/{uid}")
    @ApiOperation("获取推荐的动态id（如果历史数据不足，返回的第一个元素是-1，然后后面跟着的就是得分前五的分区id）")
    public CommonResult<List<Integer>> getRecommend(@PathVariable("uid") Integer uid) throws IOException, TasteException {
        List<Integer> integers = recommendService.recommendUpdateList(uid);
        return CommonResult.success(integers);
    }

    @GetMapping("/getRecommend/{uid}/{updateId}")
    @ApiOperation("根据当前观看的视频动态id获取推荐的动态id（如果历史数据不足，返回的第一个元素是-1，然后后面跟着的就是得分前五的分区id）")
    public CommonResult<List<Integer>> getRecommendByUpdateId(@PathVariable("uid") Integer uid,
                                                              @PathVariable("updateId") Integer updateId) throws IOException, TasteException {
        List<Integer> integers = recommendService.recommendUpdateListByUpdateId(uid,updateId);
        return CommonResult.success(integers);
    }
}
