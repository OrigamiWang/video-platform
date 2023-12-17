package szu.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import szu.AdminApplication;

@AutoConfigureMockMvc
@SpringBootTest(classes = {AdminApplication.class})
public class FfmpegUtilTest {
    String videoPath = "src/main/resources/video_test/CIMG0485.AVI";
    String outPutDir = "C:\\Users\\郑榆达\\Desktop\\";
    @Test
    void convertM3u8() throws Exception{
        try {
            VideoUtil.handleVideo("",videoPath,outPutDir,20000);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("转换失败");
        }
    }
}
