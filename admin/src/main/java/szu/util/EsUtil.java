package szu.util;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import szu.common.api.ListResult;
import szu.model.Video;
import szu.model.VideoSearchDoc;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EsUtil {
    @Resource
    private RestHighLevelClient client;
    private static final String INDICES = "video-search";
    /**
     * 仅有关键词，无排序
     * @return
     */
    public ListResult<VideoVo> getAllVideoByKeyOnly(String key){
        SearchRequest request = new SearchRequest(INDICES);
        request.source().query(QueryBuilders.matchQuery("all", key));
        SearchResponse response;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return handleResponse(response);
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
            res.add(videoVo);
        }
        return new ListResult<VideoVo>(res, total);
    }



}
