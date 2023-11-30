package szu.service;

import szu.common.api.ListResult;
import szu.dto.VideoSearchParams;
import szu.vo.VideoDetailVo;
import szu.vo.VideoVo;

import java.util.List;

public interface VideoService {
    VideoDetailVo getVideoDetail(Integer vid);

    ListResult search(VideoSearchParams params);

    /**
     * 用户个人主页的投稿展示
     * @param id 用户id
     * @param sort 排序方式 默认0，最新发布
     * @param page 分页页码 默认1
     * @param size 每页大小 默认10
     * @return
     */
    ListResult<VideoVo> getVideoById(Integer id, Integer sort, Integer page, Integer size);

    List<String> searchSuggest(String key);
}
