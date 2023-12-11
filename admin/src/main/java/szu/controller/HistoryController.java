package szu.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.dto.HisQueryDto;
import szu.dto.HistoryDto;
import szu.model.History;
import szu.model.Update;
import szu.model.Video;
import szu.service.HistoryService;
import szu.service.UpdateService;
import szu.service.UserInfoService;
import szu.vo.HistoryVo;
import szu.vo.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.controller
 * @Author: Origami
 * @Date: 2023/11/25 10:58
 */
@RestController
@Api(tags = "HistoryController")
@Tag(name = "HistoryController", description = "历史记录")
@RequestMapping("/his")
public class HistoryController {

    @Resource
    private HistoryService historyService;
    @Resource
    private UpdateService updateService;
    @Resource
    private UserInfoService userInfoService;


    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("添加历史记录")
    public CommonResult<String> addHistory(
            @ApiParam("历史记录JSON") @RequestBody HistoryDto historyDto) {
        historyService.addHistory(historyDto);
        return CommonResult.success("添加成功");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public CommonResult<String> updateHistory(
            @ApiParam("历史记录JSON") @RequestBody HistoryDto historyDto) {
        historyService.updateHistory(historyDto);
        return CommonResult.success("更新成功");
    }

    /**
     * 获取最近20条历史记录
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("获取最从cntStart到cntEnd条历史记录")
    public CommonResult<List<History>> getRecentHistory(
            @ApiParam("历史记录对应的用户和类型") @RequestBody HisQueryDto hisQueryDto,
            @ApiParam("开始条数") @RequestParam("cntStart") Integer cntStart,
            @ApiParam("结束条数") @RequestParam("cntEnd") Integer cntEnd) {
        return CommonResult.success(historyService.getRecentHistory(hisQueryDto, cntStart, cntEnd));
    }

    /**
     * 获取最近历史记录,用于历史记录列表
     */
    @GetMapping(value = "/list")
    @ApiOperation("获取最近历史记录,用于历史记录列表")
    public CommonResult<List<HistoryVo>> getRecentHistoryList(
            @ApiParam("历史记录对应的用户") @RequestBody HisQueryDto hisQueryDto) {
        //现在只有视频，所以不用判断类型
        List<History> historyList = historyService.getRecentHistory(hisQueryDto, 1, 20);
        // 通过vid查uid
        List<HistoryVo> historyVoList = new ArrayList<>();
        for (History history : historyList) {
            Update videoUpdateByVid = updateService.findVideoUpdateByVid(history.getMediaId());
            Video videoByVid = updateService.findVideoByVid(history.getMediaId());
            if (videoUpdateByVid == null||videoByVid==null) {
                System.out.println("视频不存在,vid:"+history.getMediaId());
                continue;
            }
            UserInfo up = userInfoService.getUserInfoByUid(videoUpdateByVid.getUid());
            if (up == null) {
                System.out.println("up主不存在,uid:"+videoUpdateByVid.getUid());
                continue;
            }
            //json转url数组，取第一个
            String[] coverUrls = JSON.parseObject(videoUpdateByVid.getUrls(), String[].class);
            String coverUrl;
            if (coverUrls.length == 0) {
                coverUrl = "";
            } else {
                coverUrl = coverUrls[0];
            }
            HistoryVo historyVo = new HistoryVo(history.getMediaId(), history.getWatchedAt().toString(),
                    history.getHisTime().toString(),videoByVid.getTitle(), up.getName(), coverUrl);
            historyVoList.add(historyVo);
        }
        return CommonResult.success(historyVoList);
    }
}
