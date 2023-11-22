package com.szu;


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
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import szu.CommentApplication;
import szu.model.Comment;
import szu.service.LikeService;

@SpringBootTest(classes = CommentApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CommentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试添加评论
     * @throws Exception
     */
    @Test
    void testAddComment() throws Exception {
        //构造请求参数
        Comment comment = new Comment(null,1,"XiaoMing"
                ,"test01",1,null,null
                ,0,0,null);
        String json = JSON.toJSONString(comment);
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.post("/comment/add")
                .content(json).contentType("application/json");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试点赞评论
     * @throws Exception
     */
    @Test
    void testLikeComment() throws Exception {
        //发送虚拟请求
        //点赞根评论
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/comment/like?flag=1&&pid=6550cc92743f7d2629801653&&index=-1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }
    /**
     * 测试取消点赞评论
     * @throws Exception
     */
    @Test
    void testUnLikeComment() throws Exception {
        //发送虚拟请求
        //点赞根评论
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/comment/like?flag=-1&&pid=6550cc92743f7d2629801653&&index=-1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试回复评论
     * @throws Exception
     */
    @Test
    void testReplyComment() throws Exception {
        //构造请求参数
        Comment comment = new Comment(null,2,"XiaoHong"
                ,"回复XiaoMing",1,"XiaoMing",null
                ,0,0,null);
        String json = JSON.toJSONString(comment);
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.post("/comment/reply?pid=6550cc92743f7d2629801653")
                .content(json).contentType("application/json");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }


    /**
     * 测试获取评论
     * @throws Exception
     */
    @Test
    void testListComment() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/comment/list?foreignId=1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);

        HeaderResultMatchers header = MockMvcResultMatchers.header();
        //返回的应该为json格式
        ResultMatcher contentType = header.string("Content-Type", "application/json");
        action.andExpect(contentType);


        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher result = content.json("{\"data\":[{\"id\":\"6550cc92743f7d2629801653\",\"userId\":1,\"username\":\"XiaoMing\",\"content\":\"test01\",\"foreignId\":1,\"targetUsername\":null,\"createTime\":\"2023-11-12 21:01:06\",\"likeNum\":0,\"replyNum\":0,\"children\":null}]}");
        action.andExpect(result);

    }

    @Autowired
    private LikeService likeService;
    @Test
    void insertLikeUser(){
        likeService.insertLikeUser(1);
    }
    @Test
    void testLike(){
        likeService.like(1,"2",2,-1);
    }

}
