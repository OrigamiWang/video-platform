package szu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import szu.model.VideoScore;
import szu.service.RecommendService;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
class RecommendApplicationTests {

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private RecommendService recommendService;

    @Test
    void contextLoads() throws IOException {
        FileReader fileReader = new FileReader("C:\\Users\\admin\\Desktop\\ratings.dat");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = null;
        while((s=bufferedReader.readLine())!=null){
            String[] split = s.split(",");
            recommendService.plusScore(Integer.valueOf(split[0]),Integer.valueOf(split[1]),Float.valueOf(split[2]));
        }
    }

}
