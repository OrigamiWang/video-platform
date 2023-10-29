package szu.security.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.security.dao
 * @Author: Origami
 * @Date: 2023/10/29 9:48
 */
public interface LoginDao {

    void register(@Param("name") String name, @Param("phone") String phone, @Param("pswd") String encryptedPswd);

}
