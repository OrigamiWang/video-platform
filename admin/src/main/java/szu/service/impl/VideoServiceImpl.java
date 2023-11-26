package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.service.VideoService;
import szu.vo.VideoDetailVo;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public VideoDetailVo getVideoDetail(Integer vid) {
        //TODO 查询video表与用户表，组装vo返回
        return null;
    }
}
