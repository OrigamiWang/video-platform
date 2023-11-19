package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import szu.model.User;

@Mapper
public interface UserInfoDao {

    @Select("select * from user where id = #{uid} limit 1")
    User getUserById(int uid);

}
