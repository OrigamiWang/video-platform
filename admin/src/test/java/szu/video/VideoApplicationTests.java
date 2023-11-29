package szu.video;


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
import szu.dao.UserInfoDao;
import szu.dao.VideoDao;
import szu.dto.VideoSearchParams;
import szu.model.Update;
import szu.model.User;
import szu.model.Video;
import szu.model.VideoSearchDoc;
import szu.service.VideoService;
import szu.util.EsUtil;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
    @Resource
    private VideoDao videoDao;
    @Resource
    private UpdateDao updateDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private EsUtil esUtil;
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
    @Resource
    private RestHighLevelClient client;
    @Test
    void testExistsHotelIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("video-search");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.err.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }


    /**
     * 将数据库内容组装成doc存入es
     */
    @Test
    public void initEs(){
        List<Video> videos = videoDao.selectAll();
        BulkRequest request = new BulkRequest();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Video video : videos) {
            VideoSearchDoc videoSearchDoc = new VideoSearchDoc();
            int vid = video.getId();
            BeanUtils.copyProperties(video, videoSearchDoc);
            Update update = updateDao.findByVid(vid);
            videoSearchDoc.setUploadTime(update.getUploadTime());
            videoSearchDoc.setId(update.getId());//doc里面的id是update的id
            User user = userInfoDao.getUserById(update.getUid());
            videoSearchDoc.setName(user.getName());
            List<String> sug = new ArrayList<>();
            sug.add(videoSearchDoc.getName());
            sug.add(videoSearchDoc.getTitle());
            videoSearchDoc.setSuggestion(sug);
//            videoSearchDoc.setTitle(videoSearchDoc.getTitle().replaceAll(" ",""));//去除标题中的空格
            request.add(new IndexRequest("video-search")
                    .id(videoSearchDoc.getId()+"")
                    .source(JSON.toJSONString(videoSearchDoc), XContentType.JSON)
            );
        }
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有数据
     */
    @Test
    void testMatchAll() throws IOException {
        // Request
        SearchRequest request = new SearchRequest("video-search");
        // DSL
        request.source()
                .query(QueryBuilders.matchAllQuery());
        // 发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 解析响应
        handleResponse(response);
    }

    private void handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        // 获取总条数
        long total = searchHits.getTotalHits().value;
        // 获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 遍历数组
        for (SearchHit hit : hits) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            VideoSearchDoc videoSearchDoc = JSON.parseObject(json, VideoSearchDoc.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)) {
                // 根据字段名获取高亮结果
                HighlightField highlightField = highlightFields.get("all");
                if (highlightField != null) {
                    // 获取高亮值
                    String name = highlightField.getFragments()[0].string();
                    // 覆盖非高亮结果
                    System.out.println(name);
                }
            }
            System.out.println("videoSearchDoc = " + videoSearchDoc);
        }
    }


    @Test
    void test(){
        VideoSearchParams videoSearchParams = new VideoSearchParams("原神");
        System.out.println(videoSearchParams);
//        ListResult<VideoVo> search = esUtil.search();
    }


    @Resource
    VideoService videoService;
    @Test
    void testGetVideById(){
        //结果正确
//        ListResult<VideoVo> res = videoService.getVideoById(1, 0, 1, 10);

    }
}
