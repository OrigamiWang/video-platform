package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ListResult;
import szu.common.api.ResultCode;
import szu.model.Star;
import szu.model.StarContent;
import szu.service.StarContentService;
import szu.service.StarService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: StarController
 * @Description: 收藏夹
 * @Version 1.0
 * @Date: 2023-11-23 19:06
 * @Auther: UserXin
 */

@RestController
@RequestMapping("/star")
@Api(tags = "收藏")
@Slf4j
public class StarController {

    @Resource
    private StarService starService;

    @Resource
    private StarContentService starContentService;

    /**
     * 添加收藏夹
     * @param uid 当前登录用户id
     * @param starName 收藏夹名称
     * @return
     */
    @PostMapping("/addStar/{uid}/{starName}")
    @ApiOperation("添加收藏夹")
    public CommonResult<ResultCode> addStar(@PathVariable("uid") @ApiParam("当前登录用户id") Integer uid,
                                            @PathVariable("starName") @ApiParam("收藏夹名称") String starName){
        log.info("添加收藏夹，uid：{}，starName：{}",uid,starName);
        if(starService.addStar(uid,starName)){
            return CommonResult.success(ResultCode.SUCCESS);
        }else{
            return CommonResult.success(ResultCode.FAILED);
        }

    }

    /**
     * 收藏视频
     * @param sids 收藏夹id集合
     * @param vid 要收藏的视频id
     * @return
     */
    @PostMapping("/starVideo/{vid}")
    @ApiOperation("收藏视频")
    public CommonResult<ResultCode> starVideo(@RequestBody @ApiParam("收藏夹id") List<Integer> sids,
                                              @PathVariable("vid") @ApiParam("要收藏的视频id") Integer vid){
        log.info("收藏视频sid:{},vid:{}",sids,vid);
        if(starService.starVideo(sids,vid)){
            return CommonResult.success(ResultCode.SUCCESS);
        }else{
            return CommonResult.success(ResultCode.FAILED);
        }

    }

    /**
     * 根据用户uid获取收藏夹列表
     * @param uid 当前登录的用户的id
     * @return
     */
    @GetMapping("/listStar/{uid}")
    @ApiOperation("根据用户uid获取收藏夹列表")
    public CommonResult<ListResult<Star>> listStarByUid(@PathVariable("uid") @ApiParam("当前登录的用户的id") Integer uid){
        log.info("根据用户uid获取收藏夹列表，{}",uid);
        //获取收藏夹列表
        List<Star> starList = starService.listStarByUid(uid);
        //封装统一的list返回结果
        ListResult<Star> starListResult = new ListResult<>(starList,(long) starList.size());
        return CommonResult.success(starListResult);
    }


    /**
     * 根据收藏夹id分页获取收藏夹内容
     * @param sid 收藏夹id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @GetMapping("/listStarContent/{sid}/{page}/{size}")
    @ApiOperation("根据收藏夹id分页获取收藏夹内容")
    public CommonResult<ListResult<StarContent>> listStarContentBySidByPage(@PathVariable("sid") @ApiParam("收藏夹id") Integer sid,
                                                                      @PathVariable("page") @ApiParam("当前页") Integer page,
                                                                      @PathVariable("size") @ApiParam("每页大小") Integer size){
        log.info("根据收藏夹id分页获取收藏夹内容,sid:{},page:{},size:{}",sid,page,size);
        //分页查询结果
        List<StarContent> starContentList = starContentService.listStarContentBySidByPage(sid,page,size);
        //封装统一的list返回结果
        ListResult<StarContent> starContentListResult = new ListResult<>(starContentList, (long) starContentList.size());
        return CommonResult.success(starContentListResult);
    }



}