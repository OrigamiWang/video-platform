package szu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import szu.model.UpdateType;
import szu.model.UpdateTypeExample;

public interface UpdateTypeMapper {
    long countByExample(UpdateTypeExample example);

    int deleteByExample(UpdateTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UpdateType row);

    int insertSelective(UpdateType row);

    List<UpdateType> selectByExample(UpdateTypeExample example);

    UpdateType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") UpdateType row, @Param("example") UpdateTypeExample example);

    int updateByExample(@Param("row") UpdateType row, @Param("example") UpdateTypeExample example);

    int updateByPrimaryKeySelective(UpdateType row);

    int updateByPrimaryKey(UpdateType row);
}