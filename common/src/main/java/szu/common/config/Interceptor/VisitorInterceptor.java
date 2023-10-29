package szu.common.config.Interceptor;

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

    //页面渲染后执行，记录各接口访问量以及总访问量，使用定时任务在每天的0点将redis中的数据持久化到数据库
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("访问到拦截器");
        redisService.incr("user", 1);
        System.out.println(redisService.get("user"));

        String requestURI = request.getRequestURI();
        System.out.println("请求路径：" + requestURI);
        String[] api = requestURI.split("/");
        String apiKey = api[1];//第0个是空串
        System.out.println("访问的api接口为：" + apiKey);
        //统计各api接口的访问量
        redisService.hIncr("visit", apiKey, 1L);
        //统计总访问量
        redisService.hIncr("visit", "all", 1L);
    }
}
