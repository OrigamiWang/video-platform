package szu.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dto
 * @Author: Origami
 * @Date: 2023/11/2 21:43
 */
public interface PermissionDao {

    List<Integer> getUserPermission(@Param("uid") int uid);
}
