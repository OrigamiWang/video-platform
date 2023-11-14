package szu.dao;

import org.apache.ibatis.annotations.*;
import szu.model.Permission;
import szu.model.Role;

import java.util.List;

@Mapper
public interface RoleDao {
    @Select("select * from role where name = #{name}")
    Role selectRoleByName(String name);

    int insertRole(Role role);

    @Delete("delete from role where name = #{roleName}")
    void deleteRole(String roleName);

    @Update("update role set name = #{newRole} where name = #{oldRole}")
    void updateRole(String oldRole, String newRole);

    @ResultType(Role.class)
    @Select("select * from role")
    List<Role> selectAllRole();

    @Delete("delete from role_permission where rid = #{id}")
    void deleteRoleAuth(Integer id);

    void addRolePermission(int rid, List<Integer> permission);

    @Select("select * from permission where name = #{permission}")
    Permission selectPmByName(String permission);

    @Insert("insert into permission (name) values (#{permission})")
    void insertPermission(String permission);

    @Delete("delete from permission where name = #{permission}")
    void deletePermission(String permission);

    @ResultType(Permission.class)
    @Select("select * from permission")
    List<Permission> selectAllPermission();
}
