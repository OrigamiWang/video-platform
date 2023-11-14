package szu.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import szu.dao.RoleDao;
import szu.model.Permission;
import szu.model.Role;
import szu.service.RoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Transactional
    @Override
    public boolean addRole(Role role) {
        Role seleRole = roleDao.selectRoleByName(role.getName());
        if(seleRole != null) return false;
        roleDao.insertRole(role);
        int rid = role.getId();
        //为新角色添加默认的权限信息1（role_permission）
        List<Integer> permission = new ArrayList<>();
        permission.add(1);
        roleDao.addRolePermission(rid, permission);
        return true;
    }

    @Transactional
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

    @Override
    public boolean addPermission(String permission) {
        Permission sele = roleDao.selectPmByName(permission);
        if(sele != null) return false;
        roleDao.insertPermission(permission);
        return true;
    }

    @Override
    public boolean deletePermission(String permission) {
        Permission sele = roleDao.selectPmByName(permission);
        if(sele == null) return false;
        roleDao.deletePermission(permission);
        return true;
    }

    @Override
    public List<Permission> listPermission() {
        return roleDao.selectAllPermission();
    }
}
