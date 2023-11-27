package szu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.dto.VideoSearchParams;
import szu.service.VideoService;
import szu.model.Video;
import szu.vo.VideoDetailVo;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Api("VideoController,视频相关操作")
@RequestMapping("/video")
public class VideoController {
    @Resource
    private VideoService videoService;


    @GetMapping("/page/{vid}")
    @ApiOperation("获取视频详情（点开视频详情页），传入路径参数vid")
    public CommonResult<VideoDetailVo> getPage(@PathVariable @ApiParam("视频的vid") Integer vid){
        //TODO 获取推荐视频列表
        VideoDetailVo videoVo = videoService.getVideoDetail(vid);
        if(videoVo == null){
            return CommonResult.failed();
        }
        return CommonResult.success(videoVo);
    }

    @GetMapping("/search")
    @ApiOperation("根据传参搜索视频")
    public CommonResult<List<Object>> search(@RequestParam
                                            @ApiParam("key（空串表示没有key参与搜索）、classification、sortBy、time、partition这些等于0表示默认情况即可")
                                                VideoSearchParams params)
    {
        List<Object> res = videoService.search(params);
        if(res == null) return CommonResult.failed("fail！key is null");
        return CommonResult.success(res);
    }


    @GetMapping("/suggestion")
    @ApiOperation("根据输入框输入的词，进行补全推提示")
    public List<String> suggest(@RequestParam @ApiParam("传入string类型的key，返回补全的list列表") String key){
        return null;
    }



}
