package szu;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserStatistics;

import javax.annotation.Resource;

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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }
    @Test
    @DisplayName("查询用户接口测试")
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mg/user/list"))
                .andExpect(status().isOk())
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
    /**
     * 由于id是自增，且删除用户、修改statistics、detail表都是是根据uid进行的，
     * 所以路径参数需要进行调整或者指定uid需要调整
     * 以下都需要注意修改
     * @throws Exception
     */
    @Test
    @DisplayName("修改用户statistics接口测试")
    public void test4() throws Exception {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUid(6);
        userStatistics.setFan(1);
        userStatistics.setLike(2);
        userStatistics.setFollow(3);
        mockMvc.perform(MockMvcRequestBuilders.put("/mg/user/updatestatistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJsonStr(userStatistics))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("修改用户detail接口测试")
    public void test5() throws Exception {
        UserDetail userDetail = new UserDetail();
        userDetail.setUid(6);
        userDetail.setExp(1);
        userDetail.setGender((byte)1);
        userDetail.setIp("北京");
        userDetail.setInfo("测试修改");
        mockMvc.perform(MockMvcRequestBuilders.put("/mg/user//updatedetail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJsonStr(userDetail))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }
    @Test
    @DisplayName("为用户增加角色接口测试")
    public void test6() throws Exception {
        //6为uid，2为角色id
        mockMvc.perform(MockMvcRequestBuilders.post("/mg/user/addrole/6/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("为用户删除角色接口测试")
    public void test7() throws Exception {
        //6为uid，2为角色id
        mockMvc.perform(MockMvcRequestBuilders.delete("/mg/user/deleterole/6/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("删除用户接口测试")
    public void test9() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/mg/user/delete/11"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("操作成功"))
                .andDo(print());
    }



}
