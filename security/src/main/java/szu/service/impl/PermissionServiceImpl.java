package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.dao.PermissionDao;
import szu.service.PermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service.impl
 * @Author: Origami
 * @Date: 2023/11/2 22:14
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<Integer> getUserPermission(int uid) {
        return permissionDao.getUserPermission(uid);
    }
}
