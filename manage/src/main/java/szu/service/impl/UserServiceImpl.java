package szu.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import szu.dao.UserDao;
import szu.model.User;
import szu.model.UserDetail;
import szu.model.UserRole;
import szu.model.UserStatistics;
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

    @Transactional
    @Override
    public void insert(User user) {
        userDao.insert(user);
        //新增用户时添加默认的user_detail、user_statistics、user_role信息
        Integer uid = user.getId();
        UserDetail userDetail = new UserDetail();
        userDetail.setUid(uid);
        userDao.addDefaultUserDetail(userDetail);

        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUid(uid);
        userDao.addDefaultUserStatistics(userStatistics);

        userDao.addDefaultUserRole(uid);//添加默认的权限，普通用户
    }

    @Transactional
    @Override
    public boolean deleteById(Integer id) {
        User user = userDao.selectUserById(id);
        if(user == null) return false;
        //id是user表的id，其他表的uid
        userDao.deleteUserById(id);//删除user表的信息
        userDao.deleteUserDetailById(id);
        userDao.deleteUseStatisticsById(id);
        userDao.deleteAllUserRole(id);
        return true;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUserById(user);
    }

    @Override
    public boolean addRoleById(Integer userId, Integer roleId) {
        UserRole userRole = userDao.selectUserRole(userId, roleId);
        if(userRole != null){
            return false;
        }
        userDao.addUserRole(userId, roleId);
        return true;
    }

    @Override
    public boolean deleteRoleById(Integer userId, Integer roleId) {
        UserRole userRole = userDao.selectUserRole(userId, roleId);
        if(userRole == null){
            return false;
        }
        userDao.deleteUserRole(userId, roleId);
        return true;
    }

    @Override
    public void updateUserStatistics(UserStatistics userStatistics) {
        userDao.updateUserStatistics(userStatistics);
    }

    @Override
    public void updateUserDetail(UserDetail userDetail) {
        userDao.updateUserDetail(userDetail);
    }


}
