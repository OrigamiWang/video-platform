package szu.service;

import szu.dto.VideoSearchParams;
import szu.vo.VideoDetailVo;
import szu.vo.VideoVo;

import java.util.List;
import java.util.Objects;

public interface VideoService {
    VideoDetailVo getVideoDetail(Integer vid);

    List<Object> search(VideoSearchParams params);
}
