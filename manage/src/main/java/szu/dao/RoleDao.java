package szu.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import szu.model.Role;

import java.util.List;

@Mapper
public interface RoleDao {
    @Select("select * from role where name = #{name}")
    Role selectRoleByName(String name);

    @Insert("insert into role (name) values (#{roleName})")
    void insertRole(String roleName);

    @Delete("delete from role where name = #{roleName}")
    void deleteRole(String roleName);

    @Update("update role set name = #{newRole} where name = #{oldRole}")
    void updateRole(String oldRole, String newRole);

    @ResultType(Role.class)
    @Select("select * from role")
    List<Role> selectAllRole();

    @Delete("delete from role_permission where rid = #{id}")
    void deleteRoleAuth(Integer id);
}
