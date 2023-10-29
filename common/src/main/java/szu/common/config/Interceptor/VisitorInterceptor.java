package szu.common.config.Interceptor;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import szu.common.service.RedisService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Component
public class VisitorInterceptor implements HandlerInterceptor {
    @Resource
    private RedisService redisService;

    //页面渲染后执行，记录各接口访问量以及总访问量，使用定时任务在每天的23:59将redis中的数据持久化到数据库
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String[] api = requestURI.split("/");
        String apiKey = api[1];//第0个是空串
        String today = DateUtil.today();
        String key = "visit:" + today;
        //统计各api接口的访问量
        redisService.hIncr(key, apiKey, 1L);
        //统计总访问量
        redisService.hIncr(key, "all", 1L);
        redisService.expire(key, 86400);//24h
    }
}
