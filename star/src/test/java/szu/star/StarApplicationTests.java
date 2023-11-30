package szu.star;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import szu.StarApplication;
import szu.common.service.RedisService;

import javax.annotation.Resource;

@SpringBootTest(classes = StarApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StarApplicationTests {

    @Resource
    private RedisService redisService;
    @Test
    void contextLoads() {
        redisService.del("updates:photoUpdate:3");
    }

}
