package szu.util;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import szu.common.api.ListResult;
import szu.dto.VideoSearchParams;
import szu.model.VideoSearchDoc;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * es请求工具类
 */
@Component
public class EsUtil {
    @Resource
    private RestHighLevelClient client;
    private static final String INDICES = "video-search";

//    private Long playTimeToSeconds(Time playTime){
//
//    }

    /**
     * 根据用户昵称查询投稿
     *
     */
    public ListResult<VideoVo> searchVideoByName(String name, Integer sort, Integer page, Integer size){
        SearchRequest request = new SearchRequest(INDICES);
        request.source().query(QueryBuilders.termsQuery("name", name));
        if(sort == 1){
            request.source().sort("playNum", SortOrder.DESC);
        }else if(sort == 2){
            request.source().sort("starNum", SortOrder.DESC);
        }else{
            request.source().sort("uploadTime", SortOrder.DESC);
        }
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
     * 关键词 + 限制分区
     * @return
     */
//    public ListResult<VideoVo> searchByKeyWithPid(VideoSearchParams params){
//        SearchRequest request = new SearchRequest(INDICES);
//        request.source().query(QueryBuilders.matchQuery("all", params.getKey()));
//
//
//    }







    public ListResult<VideoVo> search(VideoSearchParams params) {
        try {
            System.out.println(params);
            int page = params.getPage();
            int size = params.getSize();
            String key= params.getKey();
            int classificationId = params.getClassificationId();//分类id
            int time = params.getTime();//视频时长id
            int pid = params.getPid();//分区id
            int sortBy = params.getSortBy();//排序id
            SearchRequest request = new SearchRequest(INDICES);
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            if(classificationId != 0) {
                //分类id不为0，0则默认为视频标题
                switch (classificationId){
                    case 1:{
                        //仅查询作者名字
                        boolQuery.filter(QueryBuilders.matchQuery("name", key));
                        break;
                    }
                }
            }
            else boolQuery.must(QueryBuilders.matchQuery("all", key));
            if(time != 0){
                //按视频时长作筛选
                System.out.println(time);
            }
            if(pid != 0){
                //按分类id作筛选
                System.out.println(pid);
            }
            if(sortBy != 0){
                //将查询结果作排序处理
                System.out.println(sortBy);
            }
            request.source().query(boolQuery)
                    .highlighter(new HighlightBuilder().field("all").requireFieldMatch(false));
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response);

            return null;
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
}
