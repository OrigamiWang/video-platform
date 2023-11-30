package szu.dao;


import szu.model.Update;

import java.util.List;

public interface UpdateDao {
    List<Update> findInPage(int pageNum, int pageSize);//获取所有动态,返回List<Update>
    List<Update> findByUid(int uid);//获取指定用户的动态,返回List<Update>
    Update findById(int id);//获取指定id的动态,返回Update
    void update(int id, int vid, int uid, String content, int status, String datetime, String urls);//更新指定id的动态
    int insert(int vid, int uid, String content, int status, String datetime, String urls);//插入动态
    void deleteById(int id);//删除指定id的动态
    List<Update> findAll();

    void deleteByVid(int id);

    Update findByVid(int vid);//根据vid找到那条动态获取详细信息
}