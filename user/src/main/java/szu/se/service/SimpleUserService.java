package szu.se.service;

import szu.se.dao.SimpleUser;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.se.service
 * @Author: Origami
 * @Date: 2023/10/2 10:16
 */
public interface SimpleUserService {

    List<SimpleUser> getSimpleUserList();
}
