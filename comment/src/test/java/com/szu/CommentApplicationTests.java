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

import java.util.ArrayList;

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
                ,0,0,0,new ArrayList<>());
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
     * 测试回复评论
     * @throws Exception
     */
    @Test
    void testReplyComment() throws Exception {
        //构造请求参数
        Comment comment = new Comment(null,2,"XiaoHong"
                ,"回复XiaoMing",1,"XiaoMing",null
                ,0,0,0,new ArrayList<>());
        String json = JSON.toJSONString(comment);
        //发送虚拟请求
        MockHttpServletRequestBuilder builder
                = MockMvcRequestBuilders.post("/comment/reply/656c39643d8e1a76a7c398e6")
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
     * 测试获取评论总数
     * @throws Exception
     */
    @Test
    void testCountCommentsByForeignId() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/comment/count/1");
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
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/comment/likeRoot/1/656c39643d8e1a76a7c398e6/1/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }
    /**
     * 测试点赞子评论
     * @throws Exception
     */
    @Test
    void testUnLikeComment() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/comment/likeChildren/1/656c3b5d5410b83e9d3d2406/1/1/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试分页获取评论
     * @throws Exception
     */
    @Test
    void testListCommentByPages() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/comment/listRootComment/1/0/5/1?sortBy=likeNum");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试分页获取子评论
     * @throws Exception
     */
    @Test
    void testListChildrenCommentByPages() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/comment/listChildrenComment/656c39643d8e1a76a7c398e6/1/5/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试置顶评论
     * @throws Exception
     */
    @Test
    void testToTopComment() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/comment/toTopComment/656c39643d8e1a76a7c398e6/1");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试删除子评论
     * @throws Exception
     */
    @Test
    void testDeleteChildComment() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/comment/deleteRoot/656c3b5d5410b83e9d3d2406/");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    /**
     * 测试删除根评论
     * @throws Exception
     */
    @Test
    void testDeleteRootComment() throws Exception {
        //发送虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/comment/deleteRoot/656c39643d8e1a76a7c398e6");
        ResultActions action = mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok);
    }

    @Autowired
    private LikeService likeService;
    @Test
    void insertLikeUser(){
        likeService.insertLikeUser(2);
    }
    @Test
    void testLike(){
        likeService.like(3,"2",2,1);
    }

}
