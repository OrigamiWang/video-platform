package szu.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import szu.common.config.Interceptor.VisitorInterceptor;
import javax.annotation.Resource;

/**
 * 拦截器配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private VisitorInterceptor visitorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/visit/**")//去除管理端接口
                .excludePathPatterns("/error/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
