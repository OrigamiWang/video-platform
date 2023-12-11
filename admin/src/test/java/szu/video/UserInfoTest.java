package szu.video;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.AdminApplication;

import javax.annotation.Resource;
import java.lang.annotation.Target;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
public class UserInfoTest {
    @Resource
    private MockMvc mockMvc;

    @Test
    @DisplayName("根据id查询用户信息接口测试")
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/get-by-uid")
                        .param("uid", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    void testGetUserVideoByUid() throws Exception {
        //默认查询，第一页
        String id = "1";//uid
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/list-video-by-uid").param("uid",id))
                .andExpect(status().isOk())
                .andDo(print());
        //按播放量查询，第一页
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/list-video-by-uid")
                        .param("uid",id)
                        .param("sort", "1"))
                .andExpect(status().isOk())
                .andDo(print());
        //按播放量查询，第二页
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/list-video-by-uid")
                        .param("uid",id)
                        .param("sort", "1")
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andDo(print());
        //查询没有视频的用户
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/list-video-by-uid").param("uid","3"))
                .andExpect(status().isOk())
                .andDo(print());
        //查询不存在的用户
        //查询没有视频的用户
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info/list-video-by-uid").param("uid","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("500"))
                .andDo(print());
    }

}
