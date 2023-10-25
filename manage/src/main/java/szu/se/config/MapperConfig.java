package szu.se.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.se.config
 * @Author: Origami
 * @Date: 2023/10/2 9:53
 */
@Configuration
@MapperScan(basePackages={"szu.se.mapper"})
public class MapperConfig {
}
