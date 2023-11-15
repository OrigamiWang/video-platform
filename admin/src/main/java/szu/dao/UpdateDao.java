package szu.dao;


import szu.model.Update;

import java.util.List;

public interface UpdateDao {
    List<Update> findAll();//获取所有动态,返回List<Updates>
    List<Update> findByType(int type);//获取指定类型的动态,返回List<Updates>

    List<Update> findByUid(int uid);//获取指定用户的动态,返回List<Updates>

    List<Update> findByPid(int pid);//获取某个分区的动态,返回List<Updates>

    Update findById(int id);//获取指定id的动态,返回Updates

    void insert(int uid, String title, String content,int status, int type, String datetime, String urls, int pid);//插入新动态

    void deleteById(int id);//删除指定id的动态

    void update(int id, int uid, String title, String content,int status, int type, String datetime, String urls, int pid);//更新动态

}