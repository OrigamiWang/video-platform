package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.dto.HisQueryDto;
import szu.dto.HistoryDto;
import szu.model.History;
import szu.service.HistoryService;

import javax.annotation.Resource;
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
}
