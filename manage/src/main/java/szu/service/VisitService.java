package szu.service;

import szu.model.Visit;
import szu.model.VisitMonTotal;

import java.util.List;

public interface VisitService {
    /**
     * 持久化访问量
     * @param api
     * @param val
     */
    void save(String api, Integer val);

    /**
     * 根据时间id查询访问量
     * @param today
     * @return
     */
    List<Visit> getVisByTimeId(String today);

    /**
     * 根据年月查询月份访问总量
     * @param match
     */
    List<VisitMonTotal> getVisByMon(String match);
}
