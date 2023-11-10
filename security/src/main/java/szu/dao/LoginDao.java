package szu.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dao
 * @Author: Origami
 * @Date: 2023/10/29 9:48
 */
public interface LoginDao {

    void register(@Param("name") String name, @Param("username") String username, @Param("pswd") String encryptedPswd);

    User getUser(@Param("phone") String phone);

    void registerByPhone(@Param("name") String name, @Param("phone") String phone);

    void registerByEmail(@Param("name") String name, @Param("email") String email);

}
