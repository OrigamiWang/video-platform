package szu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import szu.model.Updates;
import szu.model.UpdatesExample;

public interface UpdatesMapper {
    long countByExample(UpdatesExample example);

    int deleteByExample(UpdatesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Updates row);

    int insertSelective(Updates row);

    List<Updates> selectByExample(UpdatesExample example);

    Updates selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Updates row, @Param("example") UpdatesExample example);

    int updateByExample(@Param("row") Updates row, @Param("example") UpdatesExample example);

    int updateByPrimaryKeySelective(Updates row);

    int updateByPrimaryKey(Updates row);
}