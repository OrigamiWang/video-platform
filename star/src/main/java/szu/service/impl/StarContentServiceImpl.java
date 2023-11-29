package szu.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import szu.dao.StarContentDao;
import szu.model.StarContent;
import szu.service.StarContentService;
import szu.service.StarService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: StarContentServiceImpl
 * @Description: 收藏夹内容Service接口实现类
 * @Version 1.0
 * @Date: 2023-11-23 19:38
 * @Auther: UserXin
 */
@Service
public class StarContentServiceImpl implements StarContentService {

    @Resource
    private StarContentDao starContentDao;

    /**
     * 根据收藏夹id分页获取收藏夹内容
     * @param sid 收藏夹id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @Override
    public List<StarContent> listStarContentBySidByPage(Integer sid, Integer page, Integer size) {
        PageHelper.startPage(page,size); //开启分页查询
        List<StarContent> starContentList = starContentDao.listStarContentBySid(sid);
        return starContentList;
    }
}
