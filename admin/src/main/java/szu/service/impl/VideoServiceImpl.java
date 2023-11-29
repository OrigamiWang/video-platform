package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.common.api.ListResult;
import szu.dao.UserInfoDao;
import szu.dto.VideoSearchParams;
import szu.model.User;
import szu.service.VideoService;
import szu.util.EsUtil;
import szu.vo.VideoDetailVo;
import szu.vo.VideoVo;

import javax.annotation.Resource;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private EsUtil esUtil;
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public VideoDetailVo getVideoDetail(Integer vid) {
        //TODO 查询video表与用户表，组装vo返回
        return null;
    }

    @Override
    public ListResult<VideoVo> search(VideoSearchParams params) {
        ListResult<VideoVo> res = esUtil.search(params);
        return null;
    }

    //根据用户的昵称查询他的投稿
    @Override
    public ListResult<VideoVo> getVideoById(Integer id, Integer sort, Integer page, Integer size) {
        User user = userInfoDao.getUserById(id);
        String name = user.getName();
        ListResult<VideoVo> videoVoListResult = esUtil.searchVideoByName(name, sort, page, size);
        System.out.println(videoVoListResult);
        return null;
    }
}
