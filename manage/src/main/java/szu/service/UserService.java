package szu.service;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/10/28 15:22
 */
public interface UserService {
    List<User> getUserList();

    void insert(User user);

    void deleteById(Integer id);

    void updateUser(User user);
}
