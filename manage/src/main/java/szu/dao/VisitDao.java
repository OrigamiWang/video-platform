package szu.dao;

public interface VisitDao {

    /**
     * 持久化访问量
     * @param api 访问api，all表示所有api的总访问
     * @param val 访问量
     * @param today yyyy-mm-dd的时间id
     */
    void save(String api, Integer val, String today);
}
