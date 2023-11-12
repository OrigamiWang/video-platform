package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.dao.RoleDao;
import szu.model.Role;
import szu.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Override
    public boolean addRole(String roleName) {
        Role seleRole = roleDao.selectRoleByName(roleName);
        if(seleRole != null) return false;
        roleDao.insertRole(roleName);
        return true;
    }

    @Override
    public boolean deleteRole(String roleName) {
        Role seleRole = roleDao.selectRoleByName(roleName);
        if(seleRole == null) return false;
        roleDao.deleteRole(roleName);
        roleDao.deleteRoleAuth(seleRole.getId());//删除角色对应的权限信息
        return true;
    }

    @Override
    public boolean updateRole(String oldRole, String newRole) {
        Role seleRole = roleDao.selectRoleByName(oldRole);
        if(seleRole == null) return false;
        roleDao.updateRole(oldRole, newRole);
        return true;
    }

    @Override
    public List<Role> listRole() {
        return roleDao.selectAllRole();
    }
}
