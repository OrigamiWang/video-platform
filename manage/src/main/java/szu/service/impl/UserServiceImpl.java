package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.dao.UserDao;
import szu.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service.impl
 * @Author: Origami
 * @Date: 2023/10/28 15:22
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;


    @Override
    public List<User> getUserList() {
        return userDao.selectAllUser();
    }


    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void deleteById(Integer id) {
        userDao.deleteUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUserById(user);
    }
}
