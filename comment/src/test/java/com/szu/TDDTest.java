package com.szu;

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
import szu.CommentApplication;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @ClassName: TDDTest
 * @Description: TODO
 * @Version 1.0
 * @Date: 2024-01-02 20:24
 * @Auther: UserXin
 */
@SpringBootTest(classes = CommentApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TDDTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试输入小于零
     * @throws Exception
     */
    @Test
    void testInputLessThanZero() throws Exception {
        //构造请求参数
        Integer foreignId = -1;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                //预期返回的json数据中的code为500
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("500"))
                .andDo(print());
    }

    /**
     * 测试输入大于等于零，点赞列表不为空，评论列表为空
     * @throws Exception
     */
    @Test
    void testInputLargerThanZeroLikeListNotNull() throws Exception {
        //构造请求参数
        Integer foreignId = 8;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list").isEmpty())
                .andDo(print());
    }

    /**
     * 测试输入大于等于零，点赞列表为空，评论列表为空
     * @throws Exception
     */
    @Test
    void testInputLargerThanZeroLikeListIsNull() throws Exception {
        //构造请求参数
        Integer foreignId = 8;
        int page = 0;
        int size = 5;
        Integer uid = 2;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list").isEmpty())
                .andDo(print());
    }

    /**
     * 测试评论列表长度为0
     * @throws Exception
     */
    @Test
    void testCommentListLengthIsZero() throws Exception {
        //构造请求参数
        Integer foreignId = 8;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list").isEmpty())
                .andDo(print());
    }

    /**
     * 测试评论列表里面的评论有在点赞列表中
     * @throws Exception
     */
    @Test
    void testHasLikeComment() throws Exception {
        //构造请求参数
        Integer foreignId = 1;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list[3].isLiked").value(true))
                .andDo(print());
    }

    /**
     * 测试评论列表里面的评论不在点赞列表中
     * @throws Exception
     */
    @Test
    void testHasNotLikeComment() throws Exception {
        //构造请求参数
        Integer foreignId = 2;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data.list[0].isLiked").value(false))
                .andDo(print());
    }


    /**
     * 测试foreignId输入大于等于零
     * @throws Exception
     */
    @Test
    void testForeignIdLargerThanZero() throws Exception {
        //构造请求参数
        Integer foreignId = 1;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list").isNotEmpty())
                .andDo(print());
    }

    /**
     * 测试page输入大于等于零
     * @throws Exception
     */
    @Test
    void testPageLEZero() throws Exception {
        //构造请求参数
        Integer foreignId = 1;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                //预期返回的json数据中的data.list为空
                .andExpect(MockMvcResultMatchers.jsonPath("data.list").isNotEmpty())
                .andDo(print());
    }



    /**
     * 测试foreignId输入小于零
     * @throws Exception
     */
    @Test
    void testForeignIdLessThanZero() throws Exception {
        //构造请求参数
        Integer foreignId = -1;
        int page = 0;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                //预期返回的json数据中的code为500
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("500"))
                .andDo(print());
    }

    /**
     * 测试page输入小于零
     * @throws Exception
     */
    @Test
    void testPageLessThenZero() throws Exception {
        //构造请求参数
        Integer foreignId = 1;
        int page = -1;
        int size = 5;
        Integer uid = 1;
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.get("/comment/listRootComment/"+foreignId+
                "/"+page+"/"+size+"/"+uid+"?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                //预期返回的json数据中的code为500
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("500"))
                .andDo(print());
    }


}


