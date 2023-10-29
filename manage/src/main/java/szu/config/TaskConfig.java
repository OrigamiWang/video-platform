package szu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import szu.common.service.RedisService;
import szu.service.VisitService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 定时任务配置类
 */
@Slf4j
@Component
public class TaskConfig {
    @Resource
    private RedisService redisService;
    @Resource
    private VisitService visitService;

    //每天23:59触发
    @Scheduled(cron = "0 59 23 * * ? ")
    public void saveVisit(){
        log.info("持久化访问量：{}",new Date());
        Map<Object, Object> visit = redisService.hGetAll("visit");
        visit.forEach((key, val) -> {
           visitService.save((String)key, (Integer)val);
        });
    }
    
}
