package szu.star;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import szu.StarApplication;
import szu.common.service.RedisService;

import javax.annotation.Resource;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = StarApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StarApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试添加收藏夹
     * @throws Exception
     */
    @Test
    void testAddStar() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/star/addStar/1/testStar");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试收藏视频
     * @throws Exception
     */
    @Test
    void testStarVideo() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/star/starVideo/1")
                .content(JSON.toJSONString(Arrays.asList("656c3e87582b71280567dbae")))
                .contentType("application/json");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    /**
     * 测试根据用户uid获取收藏夹列表
     * @throws Exception
     */
    @Test
    void testListStarByUid() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/star/listStar/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    /**
     * 测试获取当前观看的视频被当前用户收藏在哪个收藏夹下
     * @throws Exception
     */
    @Test
    void testListStared() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/star/listStared/1/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }


    /**
     * 测试删除收藏的视频
     * @throws Exception
     */
    @Test
    void testRemoveStarVideo() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/star/removeStarVideo/656c3e87582b71280567dbae/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    /**
     * 测试删除收藏夹
     * @throws Exception
     */
    @Test
    void testRemoveStar() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/star/removeStar/656c3e87582b71280567dbae");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }
}
