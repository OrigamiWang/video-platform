package szu.dao;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dao
 * @Author: Origami
 * @Date: 2023/11/25 10:33
 */

import org.apache.ibatis.annotations.Param;
import szu.model.History;

import java.sql.Time;
import java.util.List;

/**
 * 历史记录
 */
public interface HistoryDao {
    void insertHis(@Param("userId") int userId,
                   @Param("mediaId") int mediaId,
                   @Param("mediaType") int mediaType,
                   @Param("watchedAt") Time watchedAt);

    void updateHis(@Param("userId") int userId,
                   @Param("mediaId") int mediaId,
                   @Param("mediaType") int mediaType,
                   @Param("watchedAt") Time watchedAt);


    List<History> selectRecentHis(@Param("userId") int userId,
                                  @Param("mediaType") int mediaType,
                                  @Param("offset") int offset,
                                  @Param("count") int count);
}

