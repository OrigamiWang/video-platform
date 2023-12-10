package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.common.api.ListResult;
import szu.dao.UserInfoDao;
import szu.dao.VideoDao;
import szu.dto.VideoSearchParams;
import szu.model.User;
import szu.model.UserSearchDoc;
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
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private VideoDao videoDao;

    @Override
    public VideoDetailVo getVideoDetail(Integer id) {
        return videoDao.getVideoDetail(id);
    }

    @Override
    public ListResult search(VideoSearchParams params) {
        int classificationId = params.getClassificationId();//判断是搜视频还是搜作者
        if(classificationId == 0){
            return esUtil.searchVideo(params);
        }else if(classificationId == 1){
            return esUtil.searchUser(params);
        }
        return null;
    }

    @Override
    public ListResult<VideoVo> searchVideo(VideoSearchParams params) {
        return esUtil.searchVideo(params);
    }

    @Override
    public ListResult<UserSearchDoc> searchUser(VideoSearchParams params) {
        return esUtil.searchUser(params);
    }

    //根据用户的昵称查询他的投稿
    @Override
    public ListResult<VideoVo> getVideoById(Integer id, Integer sort, Integer page, Integer size) {
        User user = userInfoDao.getUserById(id);
        if(user == null) return null;
        String name = user.getName();
        ListResult<VideoVo> res = esUtil.searchVideoByName(name, sort, page, size);
        return res;
    }

    @Override
    public List<String> searchSuggest(String key) {
        return esUtil.suggest(key);
    }
}
