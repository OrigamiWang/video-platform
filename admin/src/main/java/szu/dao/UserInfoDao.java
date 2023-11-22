package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserStatistics;

@Mapper
public interface UserInfoDao {

    @Select("select * from user where id = #{uid} limit 1")
    User getUserById(int uid);
    @Select("select * from user_detail where uid = #{uid}")
    UserDetail getUserDetailById(int uid);

    @Select("select * from user_statistics where uid = #{uid}")
    UserStatistics getUserStatisticsById(int uid);
}
