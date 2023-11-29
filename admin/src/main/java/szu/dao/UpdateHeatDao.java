package szu.dao;

import org.apache.ibatis.annotations.Select;
import szu.model.UpdateHeat;

import java.util.List;

/**
 * @ClassName: UpdateHeatDao
 * @Description: UpdateHeatDao
 * @Version 1.0
 * @Date: 2023-11-29 9:07
 * @Auther: UserXin
 */
public interface UpdateHeatDao {
    @Select("SELECT * from update_heat where update_id = #{updateId}")
    UpdateHeat getByUpdateId(Integer updateId);
}
