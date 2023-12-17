package szu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.AdminApplication;
import szu.common.service.MinioService;

import javax.annotation.Resource;
import java.io.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = {AdminApplication.class})
class UpdatesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final static String KEY = "login_user_1231231412421414";
    private final static String TEST_USER_INFO = "[\"szu.model.User\",{\"id\":7,\"name\":\"匿名用户184\",\"level\":0,\"status\":0,\"password\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\",\"phone\":\"18475401312\",\"email\":null}]";
    private final static String AUTH = "1231231412421414";

    @Resource
    private MinioService minioService;

    @Test
    void publishEssay() throws Exception {
        //只发布文字
        mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
                        .param("content", "测试发布动态")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //无文字无图片
        mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        //未登录
        mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
                        .param("content", "测试发布动态"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //发布文字和图片
        String imagePath = "C:\\Users\\郑榆达\\Desktop\\bg\\gg.jpg";
        File file = new File(imagePath);
        MockMultipartFile f1 = new MockMultipartFile("images", file.getName(),
                "image/jpeg", new FileInputStream(file));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/essay")
                        .file(f1)
                        .param("content", "测试发布动态")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateEssay() throws Exception {
        //修改文字
        mockMvc.perform(MockMvcRequestBuilders.put("/updates/essay")
                        .param("content", "测试修改动态")
                        .param("updateId", "1")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //修改图片
        String imagePath = "C:\\Users\\郑榆达\\Desktop\\bg\\gg.jpg";
        File file = new File(imagePath);
        MockMultipartFile f1 = new MockMultipartFile("images", file.getName(),
                "image/jpeg", new FileInputStream(file));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/essay")
                        .file(f1)
                        .param("updateId", "1")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //修改文字和图片
        mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/essay")
                        .file(f1)
                        .param("content", "测试修改动态")
                        .param("updateId", "1")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGet() throws Exception {
        //分页获取，获取第一页
        mockMvc.perform(MockMvcRequestBuilders.get("/updates/inPage")
                        .param("pageNum", "1")
                        .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //获取所有
        mockMvc.perform(MockMvcRequestBuilders.get("/updates/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //获取指定用户的动态

    }

    @Test
    void allPartitions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/updates/partition"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/updates/homePage")
                        .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void publishVideo() throws Exception {
//        发布视频
        String videoPath = "E:\\我的图片\\Video\\WeChat_20221102154811.mp4";
        String photoPath = "E:\\我的图片\\icon\\face_happy.png";
        File file = new File(videoPath);
        MockMultipartFile f1 = new MockMultipartFile("video", file.getName(),
                "video/*", new FileInputStream(file));
        MockMultipartFile f2 = new MockMultipartFile("image", new File(photoPath).getName(),
                "image/jpg", new FileInputStream(new File(photoPath)));
        //body中放置视频文件
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/uploadMedia")
                        .file(f1)
                        .header("Authorization", AUTH))
                .andReturn();
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/changeVideoCover")
//                        .file(f2)
//                        .header("Authorization", AUTH))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/video")
                        .param("title", "测试发布视频")
                        .param("content", "测试发布视频")
                        .param("pid", "1")
                        .header("Authorization", AUTH))
                .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/updates/video")
                        .param("id", "17")
                        .header("Authorization", AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}