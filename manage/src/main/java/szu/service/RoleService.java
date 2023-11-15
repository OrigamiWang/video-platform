package szu.service;

import org.springframework.stereotype.Service;
import szu.model.Permission;
import szu.model.Role;

import java.util.List;

public interface RoleService {
    boolean addRole(Role role);

    boolean deleteRole(String roleName);

    boolean updateRole(String oldRole, String newRole);

    List<Role> listRole();

    boolean addPermission(String permission);

    boolean deletePermission(String permission);

    List<Permission> listPermission();
}
