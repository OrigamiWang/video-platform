package szu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import szu.AdminApplication;
import szu.SecurityApplication;

@AutoConfigureMockMvc
@SpringBootTest(classes = {AdminApplication.class, SecurityApplication.class})
class UpdatesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final static String KEY = "login_user_1231231412421414";
    private final static String TEST_USER_INFO = "[\"szu.model.User\",{\"id\":7,\"name\":\"匿名用户184\",\"level\":0,\"status\":0,\"password\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\",\"phone\":\"18475401312\",\"email\":null}]";
    private final static String AUTH = "1231231412421414";




    @Test
    void publishEssay() throws Exception {
        MvcResult resultText = mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
                        .param("content", "测试发布动态")
                        .header("Authorization", AUTH))
                .andReturn();
        System.out.println(resultText.getResponse().getContentAsString());

        MvcResult resultNone = mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
                        .header("Authorization", AUTH))
                .andReturn();
        System.out.println(resultNone.getResponse().getContentAsString());


        String photo_path = "C:\\Users\\郑榆达\\Desktop\\bg\\gg.jpg";
        MvcResult resultPhoto = mockMvc.perform(MockMvcRequestBuilders.multipart("/updates/essay")
                        .file("images", photo_path.getBytes())
                        .param("content", "测试发布动态")
                        .header("Authorization", AUTH))
                .andReturn();
        System.out.println(resultPhoto.getResponse().getContentAsString());
    }
}