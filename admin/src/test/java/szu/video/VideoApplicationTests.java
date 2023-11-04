package szu.video;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VideoApplicationTests {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/video/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"))
                .andDo(print());
    }

    @Test
    public void testUpload() throws Exception {
        String imagePath = "C:\\Users\\郑榆达\\Desktop\\bg\\bm.jpg";
        File file = new File(imagePath);
        MockMultipartFile f1 = new MockMultipartFile("images", file.getName(), "image/jpeg", new FileInputStream(file));
        String title = "测试标题";
        String content = "测试内容";
        String type = "测试分区";
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/updates/publish")
                        .file(f1)
                        .param("title", title)
                        .param("content", content)
                        .param("type", type))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/updates/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetByType() throws Exception {
        mockMvc.perform(get("/updates/byType").param("type", "测试分区"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/updates/delete").param("id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
public void testGetImage() throws Exception {
        mockMvc.perform(get("/updates/getImage").param("url", "http://localhost:8080/updates/image/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testUpdate() throws Exception {
        //提交文字和图片
        String imagePath = "C:\\Users\\郑榆达\\Desktop\\bg\\gg.jpg";
        File file = new File(imagePath);
        MockMultipartFile f1 = new MockMultipartFile("images", file.getName(), "image/jpeg", new FileInputStream(file));
        String type = "音乐";
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/updates/update")
                        .file(f1)
                        .param("id", "3")
                        .param("type", type))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
