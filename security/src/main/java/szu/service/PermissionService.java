package szu.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/11/2 22:13
 */
public interface PermissionService {
    List<Integer> getUserPermission(int uid);
}
