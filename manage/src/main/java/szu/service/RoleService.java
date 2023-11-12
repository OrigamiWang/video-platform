package szu.service;

import org.springframework.stereotype.Service;
import szu.model.Role;

import java.util.List;

public interface RoleService {
    boolean addRole(String roleName);

    boolean deleteRole(String roleName);

    boolean updateRole(String oldRole, String newRole);

    List<Role> listRole();
}
