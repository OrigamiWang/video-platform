package szu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import szu.model.User;
import szu.service.UserService;

import javax.annotation.Resource;

@SpringBootTest(classes = SecurityApplication.class)
@AutoConfigureMockMvc
public class TestUserInsert {

    @Resource
    UserService userService;
    @Test
    void test10(){
        User user = new User();
        user.setEmail("123");
        user.setName("测试");
        userService.insert(user);
    }
    @Test
    void test(){
        userService.deleteById(7);
    }

}
