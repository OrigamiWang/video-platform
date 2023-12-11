package szu.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import szu.model.UpdateHeat;

import java.util.List;

/**
 * @ClassName: UpdateHeatDao
 * @Description: UpdateHeatDao
 * @Version 1.0
 * @Date: 2023-11-29 9:07
 * @Auther: UserXin + #h
 */
public interface UpdateHeatDao {
    @Select("SELECT * from update_heat where update_id = #{updateId}")
    UpdateHeat getByUpdateId(Integer updateId);

    @Insert("INSERT INTO update_heat(update_id,like_num,comment_num,share_num) VALUES(#{updateId},#{likeNum},#{commentNum},#{shareNum})")
    void insert(int updateId, int likeNum, int commentNum, int shareNum);

    @Delete("DELETE FROM update_heat WHERE update_id = #{id}")
    void deleteByUpdateId(int id);
}
