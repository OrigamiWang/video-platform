package szu.dao;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserDao {
    List<User> selectAllUser();

    void insert(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUserById(Integer id);

    void updateUserById(User user);
}