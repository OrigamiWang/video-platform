package szu.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import szu.dao.VisitDao;
import szu.model.Visit;
import szu.model.VisitMonTotal;
import szu.service.VisitService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class VisitServiceImpl implements VisitService {
    @Resource
    private VisitDao visitDao;

    /**
     * 持久化访问量
     * @param api
     * @param val
     */
    @Override
    public void save(String api, Integer val) {
        String today = DateUtil.today();//yyyy-mm-dd
        visitDao.save(api,val,today);
    }
     /**
     * 根据时间id查询访问量
     * @param timeId
     * @return
     */
    @Override
    public List<Visit> getVisByTimeId(String timeId) {
        return visitDao.selectByTimeId(timeId);
    }

    /**
     * 根据年月查询月份访问总量
     * @param match
     */
    @Override
    public List<VisitMonTotal> getVisByMon(String match) {
       return visitDao.selectByMon(match);
    }
}
