package szu.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.common.api.CommonResult;
import szu.common.service.RedisService;
import szu.model.Visit;
import szu.model.VisitMonTotal;
import szu.service.VisitService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.controller
 * @Author: ljx
 * @Date: 2023/10/28 20:02
 */
@RestController
@Api(tags = "VisitController")
@Tag(name = "VisitController", description = "访问量管理")
@RequestMapping("/visit")
@Slf4j
public class VisitController {
    @Resource
    private VisitService visitService;
    @Resource
    private RedisService redisService;

    @Scheduled(cron = "0 59 23 * * ? ")
    public void saveVisit(){
        log.info("持久化访问量：{}",new Date());
        String today = DateUtil.today();
        Map<Object, Object> visit = redisService.hGetAll("visit:" + today);
        visit.forEach((key, val) -> {
            visitService.save((String)key, (Integer)val);
        });
    }

    @GetMapping("/today")
    @ApiOperation("获取今日的访问量")
    public CommonResult<Map<Object,Object>> getTodayVis(){
        String today = DateUtil.today();
        Map<Object, Object> visit = redisService.hGetAll("visit:" + today);
        return CommonResult.success(visit);
    }

    @GetMapping("/day/{timeId}")
    @ApiOperation("查找某一天的访问量")
    public CommonResult<List<Visit>> getVisByDay(@PathVariable("timeId") String timeId){
        List<Visit> list = visitService.getVisByTimeId(timeId);
        if(list == null || list.size() == 0)
            return CommonResult.failed(timeId + "访问量为空");
        return CommonResult.success(list);
    }


    @GetMapping("/year/{year}/mon/{mon}")
    @ApiOperation("根据特定的年月查询总的访问量")
    public CommonResult<List<VisitMonTotal>> getVisByMon(
            @PathVariable("year") String year, @PathVariable("mon") String mon){
        String match = year + "-" +mon;
        List<VisitMonTotal> visByMon = visitService.getVisByMon(match);
        if(visByMon == null || visByMon.size() == 0)
            return CommonResult.failed("暂无统计量");
        return CommonResult.success(visByMon);
    }

}
