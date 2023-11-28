package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.common.api.ListResult;
import szu.dto.VideoSearchParams;
import szu.service.VideoService;
import szu.util.EsUtil;
import szu.vo.VideoDetailVo;
import szu.vo.VideoVo;

import javax.annotation.Resource;

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
    public ListResult<VideoVo> search(VideoSearchParams params) {
        if("".equals(params.getKey())) return null;
        ListResult<VideoVo> res = esUtil.getAllVideoByKeyOnly(params.getKey());
        //TODO 根据指定的排序类型进行排序
        return null;
    }
}
