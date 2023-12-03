package szu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.AdminApplication;

import java.io.File;
import java.io.FileInputStream;

@AutoConfigureMockMvc
@SpringBootTest(classes = {AdminApplication.class})
class UpdatesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final static String KEY = "login_user_1231231412421414";
    private final static String TEST_USER_INFO = "[\"szu.model.User\",{\"id\":7,\"name\":\"匿名用户184\",\"level\":0,\"status\":0,\"password\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\",\"phone\":\"18475401312\",\"email\":null}]";
    private final static String AUTH = "1231231412421414";

    @Test
    void publishEssay() throws Exception {
//        //只发布文字
//        mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
//                        .param("content", "测试发布动态")
//                        .header("Authorization", AUTH))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        //无文字无图片
//        mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
//                        .header("Authorization", AUTH))
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
//        //未登录
//        mockMvc.perform(MockMvcRequestBuilders.post("/updates/essay")
//                        .param("content", "测试发布动态"))
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

    //    @GetMapping("/partition")
    //    @ApiOperation("获取所有视频分区")
    //    @ApiResponse(code = 200, message = "Partition的List")
    //    public CommonResult<List<Partition>> allPartitions() {
    //        return CommonResult.success(updatesService.getPartitions());
    //    }
    @Test
    void allPartitions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/updates/partition"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}