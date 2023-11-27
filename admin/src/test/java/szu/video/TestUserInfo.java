package szu.video;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.AdminApplication;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
public class TestUserInfo {
    @Resource
    private MockMvc mockMvc;

    @Test
    @DisplayName("根据id查询用户信息接口测试")
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/get-by-uid")
                        .param("uid", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }



}
