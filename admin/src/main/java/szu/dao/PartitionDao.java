package szu.dao;

import szu.model.Partition;

import java.util.List;

public interface PartitionDao {
    List<Partition> findAll();
    Partition findById(int id);

    void insert(String name);

    void deleteById(int id);

    void update(int id, String name);
}