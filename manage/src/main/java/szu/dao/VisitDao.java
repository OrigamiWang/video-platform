package szu.dao;

import szu.model.Visit;
import szu.model.VisitMonTotal;

import java.util.List;

public interface VisitDao {

    /**
     * 持久化访问量
     * @param api 访问api，all表示所有api的总访问
     * @param val 访问量
     * @param today yyyy-mm-dd的时间id
     */
    void save(String api, Integer val, String today);

    /**
     * 根据时间id查询访问量
     * @param timeId
     * @return
     */
    List<Visit> selectByTimeId(String timeId);

    /**
     * 根据年月查询月份访问总量
     * @param match
     */
    List<VisitMonTotal> selectByMon(String match);
}
