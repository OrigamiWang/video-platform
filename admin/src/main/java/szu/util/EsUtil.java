package szu.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import szu.common.api.ListResult;
import szu.dao.UserInfoDao;
import szu.dao.VideoDao;
import szu.dto.VideoSearchParams;
import szu.model.UserSearchDoc;
import szu.model.VideoSearchDoc;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * es工具类，调用initVideoIndices与initUserIndices完成数据和索引的初始化
 */
@Component
public class EsUtil {
    @Resource
    private RestHighLevelClient client;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private VideoDao videoDao;
    public static final String INDICES_FOR_VIDEO = "video-search";
    public static final String INDICES_FOR_USER = "user-search";


    //创建视频搜索的索引
    public void initVideoIndices() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDICES_FOR_VIDEO);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(!exists){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDICES_FOR_VIDEO);
            String filePath = "src/main/resources/indices/video-search.txt";
            String VIDEO_INDICES_MAPPING = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            createIndexRequest.source(VIDEO_INDICES_MAPPING, XContentType.JSON);
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            this.insertVideoIntoEs();
        }
        System.out.println("索引库已存在");
    }

    //创建用户搜索的索引
    public void initUserIndices() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDICES_FOR_USER);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(!exists){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDICES_FOR_USER);
            String filePath = "src/main/resources/indices/user-search.txt";
            String VIDEO_INDICES_MAPPING = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            createIndexRequest.source(VIDEO_INDICES_MAPPING, XContentType.JSON);
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            this.insertUserIntoEs();
        }
        System.out.println("索引库已存在");
    }

    /**
     * 将数据库中的用户信息插入es索引（初始化用）
     */
    public void insertUserIntoEs(){
        List<UserSearchDoc> users = userInfoDao.selectAllUserSearchDoc();
        BulkRequest request = new BulkRequest();
        for (UserSearchDoc user : users) {
            request.add(new IndexRequest(INDICES_FOR_USER)
                    .id(user.getId()+"")
                    .source(JSON.toJSONString(user), XContentType.JSON));
        }
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将数据库中的视频信息插入es（初始化用）
     */
    public void insertVideoIntoEs(){
        BulkRequest request = new BulkRequest();
        List<VideoSearchDoc> videoSearchDocs = videoDao.selectAllVideoSearchDoc();
        System.out.println(videoSearchDocs);
        for (VideoSearchDoc videoSearchDoc : videoSearchDocs) {
            videoSearchDoc.setSuggestion(Arrays.asList(videoSearchDoc.getTitle(),videoSearchDoc.getName()));
            request.add(new IndexRequest("video-search")
                    .id(videoSearchDoc.getId()+"")
                    .source(JSONUtil.toJsonStr(videoSearchDoc), XContentType.JSON));
        }

        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据用户昵称查询投稿，用于个人主页展示
     *
     */
    public ListResult<VideoVo> searchVideoByName(String name, Integer sort, Integer page, Integer size){
        SearchRequest request = new SearchRequest(INDICES_FOR_VIDEO);
        request.source().query(QueryBuilders.termsQuery("name", name));
        if(sort == 1){
            request.source().sort("playNum", SortOrder.DESC);
        }else if(sort == 2){
            request.source().sort("starNum", SortOrder.DESC);
        }else{
            request.source().sort("uploadTime", SortOrder.DESC);
        }
        request.source().sort("id", SortOrder.DESC);
        request.source().from((page - 1) * size).size(size);
        SearchResponse response;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return handleResponse(response);
    }

    /**
     * 查询某个索引下的全部字段
     * esUtil.matchAll(esUtil.INDICES_FOR_USER, UserSearchDoc.class); 查询用户索引
     * 或 esUtil.matchAll(esUtil.INDICES_FOR_VIDEO, VideoSearchDoc.class); 查询视频索引
     * @param indices INDICES_FOR_VIDEO 或 INDICES_FOR_USER
     * @param clazz VideoSearchDoc.class 或 UserSearchDoc.class
     * @param <T>
     * @throws IOException
     */
    public <T> void matchAll(String indices, Class<T> clazz) throws IOException {
        // Request
        SearchRequest request = new SearchRequest(indices);
        // DSL
        request.source()
                .query(QueryBuilders.matchAllQuery());
        // 发送请求
        SearchResponse response = this.client.search(request, RequestOptions.DEFAULT);

        // 解析响应
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
            T obj = JSON.parseObject(json, clazz);
            System.out.println("obj = " + obj);
        }
    }




    public ListResult<VideoVo> searchVideo(VideoSearchParams params) {
        try {
            int page = params.getPage();
            int size = params.getSize();
            String key= params.getKey();
            int time = params.getTime();//视频时长id
            int pid = params.getPid();//分区id
            int sortBy = params.getSortBy();//排序id
            SearchRequest request = new SearchRequest(INDICES_FOR_VIDEO);
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.must(QueryBuilders.matchQuery("all", key));
            switch (time){
                case 0:
                    break;
                case 1:
                    boolQuery.filter(QueryBuilders.rangeQuery("totalTime").lt(600));//小于10分钟（不包括10分钟）
                    break;
                case 2:
                    boolQuery.filter(QueryBuilders.rangeQuery("totalTime").gte(600).lt(1800));//10-30分钟（左闭右开）
                    break;
                case 3:
                    boolQuery.filter(QueryBuilders.rangeQuery("totalTime").gte(1800).lt(3600));//30到60分钟
                break;
                case 4:
                    boolQuery.filter(QueryBuilders.rangeQuery("totalTime").gt(3600));//60分钟以上
                    break;
            }
            if(pid != 0){
                boolQuery.filter(QueryBuilders.termQuery("pid", pid));
            }
            request.source().query(boolQuery);
            switch (sortBy){
                case 0:
                    break;
                case 1:
                    request.source().sort("playNum", SortOrder.DESC);//最多播放
                    break;
                case 2:
                    request.source().sort("uploadTime", SortOrder.DESC);//最新发布
                    break;
                case 3:
                    request.source().sort("dmNum", SortOrder.DESC);//最多弹幕
                case 4:
                    request.source().sort("startNum", SortOrder.DESC);//最多收藏
            }
            request.source().from((page-1) * size).size(size)
                    .highlighter(new HighlightBuilder().field("title").requireFieldMatch(false));
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return handleHighLightResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    private ListResult<VideoVo> handleResponse(SearchResponse response) {
        List<VideoVo> res = new ArrayList<>();
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
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(videoSearchDoc, videoVo);
            videoVo.setUpName(videoSearchDoc.getName());
            res.add(videoVo);
        }
        return new ListResult<VideoVo>(res, total);
    }
    //处理带高亮标签的返回结果
    private ListResult<VideoVo> handleHighLightResponse(SearchResponse response) {
        List<VideoVo> res = new ArrayList<>();
        SearchHits searchHits = response.getHits();
        // 获取总条数
        long total = searchHits.getTotalHits().value;
        // 获取文档数组
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            VideoSearchDoc videoSearchDoc = JSON.parseObject(json, VideoSearchDoc.class);
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(videoSearchDoc, videoVo);
            videoVo.setUpName(videoSearchDoc.getName());
            videoVo.setTotalTime(TimeUtil.secondsToHHMMSS(videoSearchDoc.getTotalTime()));
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)) {
                // 根据字段名获取高亮
                HighlightField highlightField = highlightFields.get("title");
                if (highlightField != null) {
                    // 获取高亮
                    String title = highlightField.getFragments()[0].string();
                    // 覆盖原结果
                    videoVo.setTitle(title);
                }
            }
            res.add(videoVo);
        }
        return new ListResult<>(res, total);
    }


    public ListResult<UserSearchDoc> searchUser(VideoSearchParams params) {
        try {
            int page = params.getPage();
            int size = params.getSize();
            String key= params.getKey();
            int sortBy = params.getSortBy();

            SearchRequest request = new SearchRequest(INDICES_FOR_USER);
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.must(QueryBuilders.matchQuery("name", key));
            request.source().query(boolQuery);
            switch (sortBy){
                case 0:
                    break;
                case 1:
                    request.source().sort("fan", SortOrder.DESC);//粉丝数由高到低
                    break;
                case 2:
                    request.source().sort("fan", SortOrder.ASC);//粉丝数由低到高
                    break;
                case 3:
                    request.source().sort("level", SortOrder.DESC);//等级由高到低
                case 4:
                    request.source().sort("level", SortOrder.ASC);//等级由低到高
            }
            request.source().from((page-1) * size).size(size);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return handleUserResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ListResult<UserSearchDoc> handleUserResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        // 获取总条数
        long total = searchHits.getTotalHits().value;
        SearchHit[] hits = searchHits.getHits();
        List<UserSearchDoc> res = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            UserSearchDoc userVo = JSON.parseObject(json, UserSearchDoc.class);
            res.add(userVo);
        }
        return new ListResult<UserSearchDoc>(res, total);
    }

    public List<String> suggest(String key) {
        try {
            SearchRequest request = new SearchRequest(INDICES_FOR_VIDEO);
            request.source().suggest(
                    new SuggestBuilder().addSuggestion(
                            "searchSuggest", SuggestBuilders.completionSuggestion("suggestion")
                                    .skipDuplicates(true)
                                    .prefix(key, Fuzziness.AUTO)
                                    .size(10)
                    )
            );
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            Suggest suggest = response.getSuggest();
            Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion = suggest.getSuggestion("searchSuggest");
            Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry = suggestion.getEntries().get(0);
            List<String> res = new ArrayList<>();
            for (Suggest.Suggestion.Entry.Option option : entry) {
                String result = option.getText().toString();
                result = result.replace(key, "<em>" + key + "</em>");
                res.add(result);
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //TODO 同步数据库与ES索引
}
