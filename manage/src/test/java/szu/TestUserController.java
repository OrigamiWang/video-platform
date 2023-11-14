package szu;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.model.User;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ManageApplication.class)
@AutoConfigureMockMvc
public class TestUserController {

    @Resource
    private MockMvc mockMvc;


    @Test
    @DisplayName("新增用户接口测试")
    public void test1() throws Exception {
        User user = new User();
        user.setName("御坂美琴");
        user.setLevel(4);
        user.setPhone("10086");
        user.setEmail("123@qq.com");
        user.setPassword("123");
        user.setStatus(0);
        mockMvc.perform(MockMvcRequestBuilders.post("/mg/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJsonStr(user))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }
    @Test
    @DisplayName("查询用户接口测试")
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mg/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }


    @Test
    @DisplayName("修改用户接口测试")
    public void test3() throws Exception {
        User user = new User();
        user.setName("御坂美琴");
        user.setLevel(4);
        user.setPhone("10086");
        user.setEmail("123@qq.com");
        user.setPassword("123456");
        user.setStatus(0);
        mockMvc.perform(MockMvcRequestBuilders.put("/mg/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJsonStr(user))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("删除用户接口测试")
    public void test4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/mg/user/delete/11"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("操作成功"))
                .andDo(print());
    }


}
