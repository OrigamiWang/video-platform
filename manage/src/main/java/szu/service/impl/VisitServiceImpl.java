package szu.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import szu.dao.VisitDao;
import szu.service.VisitService;

import javax.annotation.Resource;

@Slf4j
@Service
public class VisitServiceImpl implements VisitService {
    @Resource
    private VisitDao visitDao;

    @Override
    public void save(String api, Integer val) {
        String today = DateUtil.today();//yyyy-mm-dd
        visitDao.save(api,val,today);
    }
}
