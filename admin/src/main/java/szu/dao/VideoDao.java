package szu.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import szu.model.Video;

@Mapper
public interface VideoDao {
    @Insert("INSERT INTO video (url,play_num,dm_num,total_time,title,pid,star_num,coin_num) " +
            "VALUES (#{url},#{playNum},#{dmNum},#{totalTime},#{title},#{pid},#{starNum},#{coinNum})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Video video);//返回插入的id

    @Select("SELECT * FROM video WHERE id=#{id}")
    Video findById(int id);//根据id查找视频

    @Delete("DELETE FROM video WHERE id=#{id}")
    void deleteById(int id);
}

