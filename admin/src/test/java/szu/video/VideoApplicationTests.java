package szu.video;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import szu.AdminApplication;
import szu.common.api.ListResult;
import szu.dao.UpdateDao;
import szu.dao.UserDao;
import szu.dao.UserInfoDao;
import szu.dao.VideoDao;
import szu.dto.VideoSearchParams;
import szu.model.*;
import szu.service.RedisWithMysql;
import szu.service.VideoService;
import szu.util.EsUtil;
import szu.util.TimeUtil;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
class VideoApplicationTests {
    @Autowired
    private MockMvc mockMvc;


    @Resource
    private RedisWithMysql redisWithMysql;

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
