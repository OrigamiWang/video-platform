package szu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import szu.model.UpdateExample;

public interface UpdateMapper {
    long countByExample(UpdateExample example);

    int deleteByExample(UpdateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Update row);

    int insertSelective(Update row);

    List<Update> selectByExample(UpdateExample example);

    Update selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Update row, @Param("example") UpdateExample example);

    int updateByExample(@Param("row") Update row, @Param("example") UpdateExample example);

    int updateByPrimaryKeySelective(Update row);

    int updateByPrimaryKey(Update row);
}