package szu.video;


import com.alibaba.fastjson.JSON;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import szu.model.Update;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VideoApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Test//测试发布动态接口
    public void testPublish() throws Exception {
        //提交文字和图片
        String imagePath = "C:\\Users\\郑榆达\\Desktop\\bg\\gg.jpg";
        File file = new File(imagePath);
        MockMultipartFile f1 = new MockMultipartFile("images", file.getName(),
                "image/jpeg", new FileInputStream(file));
        String title = "测试标题";
        String content = "测试内容";
        int partition = 1;
        int type = 0;
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/updates/publish")
                        .file(f1)
                        .param("title", title)
                        .param("content", content)
                        .param("type", String.valueOf(type))
                        .param("pid", String.valueOf(partition)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test//deleteById & all
    public void testDelete_p_all() throws Exception {
        MvcResult result = mockMvc.perform(get("/updates/all")
                        .param("id", "1"))
                .andReturn();
        mockMvc.perform(get("/updates/byPartition")
                        .param("pid", "1"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/updates/delete")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test//getImages & update
    public void testGetImages_update() throws Exception {
        MvcResult result = mockMvc.perform(get("/updates/getImage")
                        .param("url", "1699838310613gg.jpg"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        //提交文字和图片
        String imagePath = "C:\\Users\\郑榆达\\Desktop\\bg\\gg.jpg";
        File file = new File(imagePath);
        MockMultipartFile f1 = new MockMultipartFile("images", file.getName(),
                "image/jpeg", new FileInputStream(file));
        String title = "测试标题";
        String content = "测试修改";
        int id = 2;
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/updates/update")
                        .file(f1)
                        .param("id", String.valueOf(id))
                        .param("title", title)
                        .param("content", content))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
