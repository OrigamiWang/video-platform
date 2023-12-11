package szu.video;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;
import szu.AdminApplication;
import szu.common.api.CommonResult;
import szu.common.api.ListResult;
import szu.dao.VideoDao;
import szu.model.Barrage;
import szu.util.EsUtil;
import szu.vo.BarrageVo;
import szu.vo.VideoVo;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
public class VideoControllerTest {
    @Resource
    private MockMvc mockMvc;
    @Resource
    private VideoDao videoDao;

    /**
     * 视频搜索测试
     * @throws Exception
     */
    @Test
    void testSearchVideo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/video/search")
                                .param("key", "御坂")
                                .param("time", "2")//10-30分钟
                                .param("sortBy", "2")//最新发布
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/video/search")
                        .param("key", "御坂")
                        .param("time", "1")//10分钟以下
                        .param("sortBy", "2")//最新发布
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/video/search")
                        .param("key", "南区")
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
                        .param("sortBy", "3")//等级由高到低
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/video/search")
                        .param("key", "御坂")
                        .param("classificationId", "1")
                        .param("sortBy", "1")//粉丝数由高到低
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
                .param("key", "南区"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/video/suggest")
                        .param("key", "nq"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/video/suggest")
                        .param("key", "nqs"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/video/suggest")
                        .param("key", "nqss"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 视频详情搜索测试
     */
    @Test
    void testGetVideoDetail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/video/detail/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }






//    @Resource
//    RabbitTemplate rabbitTemplate;
//    @Test
//    void testUpdateVideo() {
//        System.out.println(Thread.currentThread().getId());
//        System.out.println("进行测试");
//        Video video = new Video();
//        video.setId(3);
//        video.setUrl("www.new.jpg");
//        videoDao.updateVideoByVid(video);
//        rabbitTemplate.convertAndSend(MQConstant.VIDEO_EXCHANGE,MQConstant.VIDEO_UPDATE_KEY, video.getId()+"");
//    }

    @Resource
    EsUtil esUtil;
//    @Test
//    void testecs() throws IOException {
//        esUtil.initVideoIndices();
//    }
//    @Test
//    void testecs() throws IOException {
//        esUtil.initUserIndices();
//    }

    @Test
    void testGetBarrage(){
        List<BarrageVo> barrageByVid = videoDao.getBarrageByVid(1);
        System.out.println(barrageByVid);
    }
    @Test
    void testInsertBarrage(){
        Barrage barrage = new Barrage();
        barrage.setVid(1);
        barrage.setUid(1);
        barrage.setColor("#ff00ff");
        barrage.setTime(10);
        barrage.setText("测试弹幕");
        videoDao.saveBarrage(barrage);
    }

    @Test
    void testGetBarrageListByVid() throws IOException {
        List<Barrage> barrageListByVid = videoDao.getBarrageListByVid(1, 0, 5);
        System.out.println(barrageListByVid);
    }
}
