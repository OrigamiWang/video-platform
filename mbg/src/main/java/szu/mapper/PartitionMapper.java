package szu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import szu.model.Partition;
import szu.model.PartitionExample;

public interface PartitionMapper {
    long countByExample(PartitionExample example);

    int deleteByExample(PartitionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Partition row);

    int insertSelective(Partition row);

    List<Partition> selectByExample(PartitionExample example);

    Partition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Partition row, @Param("example") PartitionExample example);

    int updateByExample(@Param("row") Partition row, @Param("example") PartitionExample example);

    int updateByPrimaryKeySelective(Partition row);

    int updateByPrimaryKey(Partition row);
}