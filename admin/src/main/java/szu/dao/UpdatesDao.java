package szu.dao;

import szu.model.Updates;

import java.util.List;

public interface UpdatesDao {
    /***
     * 插入新动态
     */
    void insert(int uid, String title, String content, String type, String dateTime, String urls);
    /***
     * 删除动态
     */
    void deleteById(int id);
    /***
     * 更新动态
     */
    void update(int id,int uid, String title, String content, String type, String dateTime, String urls);
    /***
     * 获取所有动态
     */
    List<Updates> findAll();

    /***
     * 获取指定分区的动态
     */
    List<Updates> findByType(String type);

    /***
     * 获取指定动态
     */
    Updates findById(int id);
}
