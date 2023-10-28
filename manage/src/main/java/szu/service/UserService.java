package szu.service;

import szu.model.User;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/10/28 15:22
 */
public interface UserService {
    List<User> getUserList();
}
