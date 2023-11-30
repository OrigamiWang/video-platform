package szu.video;

import cn.hutool.json.JSONUtil;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.AdminApplication;
import szu.dto.VideoSearchParams;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
public class SearchTest {
    @Resource
    private MockMvc mockMvc;

    /**
     * 视频搜索测试
     * @throws Exception
     */
    @Test
    void testSearchVideo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/video/search")
                                .param("key", "原神 测试")
                                .param("sortBy", "2")//最新发布
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    /**
     * 用户搜索测试
     */
    @Test
    void testUserSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/video/search")
                        .param("key", "御坂")
                        .param("classificationId", "1")
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    /**
     * 补全提示测试
     */
    @Test
    void testSuggest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/video/suggest")
                .param("key", "深圳"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
