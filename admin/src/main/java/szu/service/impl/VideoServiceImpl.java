package szu.service.impl;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;
import szu.dto.VideoSearchParams;
import szu.service.VideoService;
import szu.util.EsUtil;
import szu.vo.VideoDetailVo;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private EsUtil esUtil;

    @Override
    public VideoDetailVo getVideoDetail(Integer vid) {
        //TODO 查询video表与用户表，组装vo返回
        return null;
    }

    @Override
    public List<Object> search(VideoSearchParams params) {
        if("".equals(params.getKey())) return null;

        List<Object> res = esUtil.getAllVideoByKeyOnly(params.getKey());
        return null;
    }
}
