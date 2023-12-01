package szu.dao;


import org.apache.ibatis.annotations.*;
import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserRole;
import szu.model.UserStatistics;

import java.util.List;
@Mapper
public interface UserDao {
    List<User> selectAllUser();

    //仅插入name与email
    void insert(User user);
    @Select("select * from user where id = #{id}")
    User selectUserById(Integer id);

    @Delete("delete from user where id = #{id}")
    void deleteUserById(Integer id);
    @Delete("delete from user_detail where uid = #{id}")
    void deleteUserDetailById(Integer id);
    @Delete("delete from user_statistics where uid = #{id}")
    void deleteUseStatisticsById(Integer id);

    void updateUserById(User user);

    @Insert("insert into user_detail (uid) values (#{uid})")
    void addDefaultUserDetail(UserDetail userDetail);

    @Insert("insert into user_role (uid, rid) values (#{uid}, 1)")
    void addDefaultUserRole(Integer uid);

    @Insert("insert into user_statistics (uid) values (#{uid})")
    void addDefaultUserStatistics(UserStatistics userStatistics);

    @Delete("delete from user_role where uid = #{uid} and rid = #{rid}")
    void deleteUserRole(Integer uid, Integer rid);
    @Delete("delete from user_role where uid = #{id}")
    void deleteAllUserRole(Integer id);

    /**
     * 查询某个角色是否拥有某个权限，有则返回UserRole对象，否则null
     */
    @Select("select * from user_role where uid = #{userId} and rid = #{roleId}")
    UserRole selectUserRole(Integer userId, Integer roleId);

    @Insert("insert into user_role (uid, rid) values (#{uid}, #{rid})")
    void addUserRole(Integer uid, Integer rid);



    void updateUserStatistics(UserStatistics userStatistics);

    void updateUserDetail(UserDetail userDetail);


}