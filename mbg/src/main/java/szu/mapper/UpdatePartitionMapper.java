package szu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import szu.model.UpdatePartition;
import szu.model.UpdatePartitionExample;

public interface UpdatePartitionMapper {
    long countByExample(UpdatePartitionExample example);

    int deleteByExample(UpdatePartitionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UpdatePartition row);

    int insertSelective(UpdatePartition row);

    List<UpdatePartition> selectByExample(UpdatePartitionExample example);

    UpdatePartition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") UpdatePartition row, @Param("example") UpdatePartitionExample example);

    int updateByExample(@Param("row") UpdatePartition row, @Param("example") UpdatePartitionExample example);

    int updateByPrimaryKeySelective(UpdatePartition row);

    int updateByPrimaryKey(UpdatePartition row);
}