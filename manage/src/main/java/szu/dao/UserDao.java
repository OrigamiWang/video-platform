package szu.dao;


import szu.model.User;

import java.util.List;

public interface UserDao {
    List<User> selectAllUser();
}