package szu.service;

import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserStatistics;

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

    boolean deleteById(Integer id);

    void updateUser(User user);

    boolean addRoleById(Integer userId, Integer roleId);

    boolean deleteRoleById(Integer userId, Integer roleId);

    void updateUserStatistics(UserStatistics userStatistics);

    void updateUserDetail(UserDetail userDetail);
}