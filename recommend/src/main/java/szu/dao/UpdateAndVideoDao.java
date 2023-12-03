package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName: updateDao
 * @Description: updateDao
 * @Version 1.0
 * @Date: 2023-12-01 11:11
 * @Auther: UserXin
 */
@Mapper
public interface UpdateAndVideoDao {
    @Select("SELECT pid from updates , video where updates.vid = video.id and updates.id = #{updateId}")
    Integer getPidByUpdateId(Integer updateId);

}
