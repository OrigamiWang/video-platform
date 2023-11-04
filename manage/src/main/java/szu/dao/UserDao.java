package szu.dao;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import szu.model.User;

import java.util.List;

public interface UserDao {
    List<User> selectAllUser();

    @Insert("insert into user " +
            "(name, gender, level, exp, status, role, fan, follow, like, ip, phone, password, info) " +
            "values " +
            "(#{name}, #{gender},#{level},#{exp},#{status},#{role},#{fan},#{follow},#{like}, #{ip},#{phone}, #{password}, #{info}")
    void insert(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUserById(Integer id);

    void updateUserById(User user);
}