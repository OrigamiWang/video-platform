package szu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.security.config
 * @Author: Origami
 * @Date: 2023/10/29 10:50
 */
//将默认加载的登录页配置删除
@Configuration
public class LoginFilter {
    @Bean
    public WebSecurityConfigurerAdapter loginPageConfig() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.removeConfigurer(DefaultLoginPageConfigurer.class);
            }
        };
    }


}
