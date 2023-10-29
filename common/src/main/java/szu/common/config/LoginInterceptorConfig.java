package szu.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import szu.common.interceptor.LoginInterceptor;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.config
 * @Author: Origami
 * @Date: 2023/10/29 21:38
 */
@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {
    /**
     * delete springboot security default page
     */
    @Bean
    public WebSecurityConfigurerAdapter loginPageConfig() {

        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.removeConfigurer(DefaultLoginPageConfigurer.class);
            }
        };
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }
}
