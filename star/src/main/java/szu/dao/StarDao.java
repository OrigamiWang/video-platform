package szu.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import szu.model.Star;

import java.util.List;

/**
 * @ClassName: StarDao
 * @Description: 收藏夹Dao接口
 * @Version 1.0
 * @Date: 2023-11-23 19:38
 * @Auther: UserXin
 */
@Mapper
public interface StarDao {
    @Insert("INSERT INTO star (uid, star_name, star_num) values (#{uid},#{starName},0)")
    int addStar(Integer uid, String starName);

    @Select("SELECT * FROM star WHERE uid = #{uid}")
    List<Star> listStarByUid(Integer uid);

    @Update("UPDATE star set star_num = star_num + 1 where id = #{sid}")
    int updateStarNumById(Integer sid);
}
