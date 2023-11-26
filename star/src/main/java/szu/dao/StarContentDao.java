package szu.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import szu.model.StarContent;

import java.util.List;

/**
 * @ClassName: StarContentDao
 * @Description: 收藏夹内容Dao接口
 * @Version 1.0
 * @Date: 2023-11-23 19:40
 * @Auther: UserXin
 */
@Mapper
public interface StarContentDao {
    @Insert("INSERT INTO star_content (sid, vid,star_date) values (#{sid},#{vid},#{starDate})")
    int starVideo(StarContent starContent);

    @Select("SELECT * FROM star_content where sid = #{sid}")
    List<StarContent> listStarContentBySid(Integer sid);
}
