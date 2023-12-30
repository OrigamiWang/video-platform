package szu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.simpleframework.xml.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ListResult;
import szu.common.service.MinioService;
import szu.dto.VideoSearchParams;
import szu.model.Barrage;
import szu.model.UserSearchDoc;
import szu.service.VideoService;
import szu.vo.BarrageVo;
import szu.vo.VideoDetailVo;
import szu.vo.VideoInfoVo;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@RestController
@Api("VideoController,视频相关操作")
@RequestMapping("/video")
public class VideoController {
    @Resource
    private VideoService videoService;
    @Resource
    private MinioService minioService;


    @GetMapping("/detail/{id}")
    @ApiOperation("获取视频详情（点开视频详情页），传入路径参数id")
    public CommonResult<VideoDetailVo> getVideoDetail(@PathVariable @ApiParam("动态id") Integer id){
        VideoDetailVo videoVo = videoService.getVideoDetail(id);
        if(videoVo == null){
            return CommonResult.failed("视频不存在");
        }
        return CommonResult.success(videoVo);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("获取视频所有信息组合，传入路径参数id")
    public CommonResult<VideoInfoVo> getVideoInfo(@PathVariable @ApiParam("视频id") Integer id){
        VideoInfoVo videoVo = videoService.getVideoInfoById(id);
        if(videoVo == null){
            return CommonResult.failed("视频不存在");
        }
        return CommonResult.success(videoVo);
    }


    @GetMapping("/search")
    @ApiOperation("根据传参搜索视频，除key外其他值均有默认值，可不传")
    /**
     * 这里不指定ListResult具体类型是因为返回的可能是VideoVo也可能是UserVo
     */
    public CommonResult<ListResult> search(@RequestParam @ApiParam("key，不能为空，搜索关键字") String key,
                                           @RequestParam(required = false, defaultValue = "0") @ApiParam("classificationId，0表示搜索视频，1表示搜索用户，默认0") int classificationId,
                                           @RequestParam(required = false, defaultValue = "0") @ApiParam("time，默认0，不作区分，1-10分钟以内，2-10到30分钟，3-30到60分钟，4-60分钟以上") int time,
                                           @RequestParam(required = false, defaultValue = "0") @ApiParam("pid，分区id，默认为0，不作区分") int pid,
                                           @RequestParam(required = false, defaultValue = "0") @ApiParam("sortBy，排序方式，默认0不排序，1-最多播放、2-最新发布、3-最多弹幕、4-最多收藏" +
                                                   "如果分类id为1表示搜索用户，此时1-粉丝数高到低、2-粉丝数低到高、3-等级高到低，4-等级低到高") int sortBy,
                                           @RequestParam(required = false, defaultValue = "1") @ApiParam("page，页码，默认1") int page,
                                           @RequestParam(required = false, defaultValue = "30") @ApiParam("size，每页大小，默认30") int size
                                           ) {
        VideoSearchParams params = new VideoSearchParams(key, classificationId, time, pid, sortBy, page, size);
        ListResult res = videoService.search(params);
        if(res == null) return CommonResult.failed();
        return CommonResult.success(res);
    }

    @GetMapping("/search/video")
    @ApiOperation("根据传参搜索视频，除key外其他值均有默认值，可不传")
    public CommonResult<ListResult<VideoVo>> searchVideo(@RequestParam @ApiParam("key，不能为空，搜索关键字") String key,
                                                         @RequestParam(required = false, defaultValue = "0") @ApiParam("time，默认0，不作区分，1-10分钟以内，2-10到30分钟，3-30到60分钟，4-60分钟以上") int time,
                                                         @RequestParam(required = false, defaultValue = "0") @ApiParam("pid，分区id，默认为0，不作区分") int pid,
                                                         @RequestParam(required = false, defaultValue = "0") @ApiParam("sortBy，排序方式，默认0不排序，1-最多播放、2-最新发布、3-最多弹幕、4-最多收藏" +
                                                   "如果分类id为1表示搜索用户，此时1-粉丝数高到低、2-粉丝数低到高、3-等级高到低，4-等级低到高") int sortBy,
                                                         @RequestParam(required = false, defaultValue = "1") @ApiParam("page，页码，默认1") int page,
                                                         @RequestParam(required = false, defaultValue = "30") @ApiParam("size，每页大小，默认30") int size
    ) {
        VideoSearchParams params = new VideoSearchParams(key, 0, time, pid, sortBy, page, size);
        ListResult<VideoVo> res = videoService.searchVideo(params);
        if(res == null) return CommonResult.failed();
        return CommonResult.success(res);
    }

    @GetMapping("/search/user")
    @ApiOperation("根据传参搜索视频，除key外其他值均有默认值，可不传")
    public CommonResult<ListResult<UserSearchDoc>> searchUser(@RequestParam @ApiParam("key，不能为空，搜索关键字") String key,
                                                         @RequestParam(required = false, defaultValue = "0") @ApiParam("time，默认0，不作区分，1-10分钟以内，2-10到30分钟，3-30到60分钟，4-60分钟以上") int time,
                                                         @RequestParam(required = false, defaultValue = "0") @ApiParam("pid，分区id，默认为0，不作区分") int pid,
                                                         @RequestParam(required = false, defaultValue = "0") @ApiParam("sortBy，排序方式，默认0不排序，1-最多播放、2-最新发布、3-最多弹幕、4-最多收藏" +
                                                                 "如果分类id为1表示搜索用户，此时1-粉丝数高到低、2-粉丝数低到高、3-等级高到低，4-等级低到高") int sortBy,
                                                         @RequestParam(required = false, defaultValue = "1") @ApiParam("page，页码，默认1") int page,
                                                         @RequestParam(required = false, defaultValue = "30") @ApiParam("size，每页大小，默认30") int size
    ) {
        VideoSearchParams params = new VideoSearchParams(key, 1, time, pid, sortBy, page, size);
        ListResult<UserSearchDoc> res = videoService.searchUser(params);
        if(res == null) return CommonResult.failed();
        return CommonResult.success(res);
    }


    @GetMapping("/suggest")
    @ApiOperation("根据输入框输入的词，进行补全推提示")
    public List<String> suggest(@RequestParam @ApiParam("传入string类型的key，返回补全的list列表") String key){
        return videoService.searchSuggest(key);
    }

    @GetMapping("/getVideoSource")
    public CommonResult<String> getVideoSource(){
        return null;
    }
    @ApiModelProperty("获取视频的弹幕数组，封装给danmu的")
    @GetMapping("/getBarrageVoByVid")
    public CommonResult<List<BarrageVo>> getBarrageByVid(@RequestParam @ApiParam("视频id") int vid){
        List<BarrageVo> barrageVoList = videoService.getBarrageByVid(vid);
        return CommonResult.success(barrageVoList);
    }
    @ApiModelProperty("保存弹幕信息")
    @PostMapping("/saveBarrage")
    public CommonResult saveBarrage(@RequestBody Barrage barrage){
        videoService.saveBarrage(barrage);
        return CommonResult.success("保存成功");
    }
    @ApiModelProperty("获取视频右侧折叠的弹幕列表")
    @GetMapping("/getBarrageList")
    public CommonResult<List<Barrage>> getBarrageListByVid(@RequestParam @ApiParam("视频id") int vid, @RequestParam @ApiParam("页码") int page, @RequestParam @ApiParam("每页数量") int size){
        List<Barrage> barrageList = videoService.getBarrageListByVid(vid, page, size);
        return CommonResult.success(barrageList);
    }


    @ApiOperation("获取视频文件流")
    @GetMapping("/getVideo/{bucketName}/{objectName:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> getVideo(@PathVariable @ApiParam("bucketName") String bucketName,
                                                                         @PathVariable @ApiParam("objectName") String objectName) {
        try {
            return minioService.viewImage(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
