package szu.service;

import szu.common.api.ListResult;
import szu.dto.VideoSearchParams;
import szu.model.Barrage;
import szu.vo.BarrageVo;
import szu.model.UserSearchDoc;
import szu.vo.VideoDetailVo;
import szu.vo.VideoInfoVo;
import szu.vo.VideoVo;

import java.util.List;

public interface VideoService {
    VideoDetailVo getVideoDetail(Integer id);
    VideoInfoVo getVideoInfoById(Integer id);

    ListResult search(VideoSearchParams params);
    ListResult<VideoVo> searchVideo(VideoSearchParams params);
    ListResult<UserSearchDoc> searchUser(VideoSearchParams params);

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

    List<BarrageVo> getBarrageByVid(int vid);

    void saveBarrage(Barrage barrage);

    List<Barrage> getBarrageListByVid(int vid, int page, int size);
}
