package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserSearchDoc;
import szu.model.UserStatistics;

import java.util.List;

@Mapper
public interface UserInfoDao {

    @Select("select * from user where id = #{uid} limit 1")
    User getUserById(int uid);
    @Select("select * from user_detail where uid = #{uid}")
    UserDetail getUserDetailById(int uid);

    @Select("select * from user_statistics where uid = #{uid}")
    UserStatistics getUserStatisticsById(int uid);

    @Select("select name from user where id=#{id}")
    String getNameById(int id);

    /**
     * 查询出UserSearchDoc，存入es的对象
     * @return
     */
    @Select("select u1.id, u1.name, u1.level, u2.fan, u2.video_num, u3.info " +
            "from user u1 " +
            "left join user_statistics u2 ON u1.id = u2.uid " +
            "left join user_detail u3 ON u2.uid = u3.uid;")
    List<UserSearchDoc> selectAllUserSearchDoc();
}
