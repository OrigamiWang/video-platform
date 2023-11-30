package szu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import szu.AdminApplication;
import szu.common.service.RedisService;
import szu.service.UpdateService;

import javax.annotation.Resource;

@AutoConfigureMockMvc
@SpringBootTest(classes = AdminApplication.class)
class UpdatesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach//登录
    void setUp() {
        MvcResult loginResult = null;
        try {
            loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                            .param("username", "your_username")
                            .param("password", "your_password"))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String token = loginResult.getResponse().getHeader("Authorization");
    }


    @Resource
    private UpdateService updatesService;
    @Resource
    private RedisService redisService;
    @Test
    void publish() throws Exception {

    }

}