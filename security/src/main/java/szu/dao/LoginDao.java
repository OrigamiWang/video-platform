package szu.dao;

import org.apache.ibatis.annotations.Param;
import szu.model.User;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dao
 * @Author: Origami
 * @Date: 2023/10/29 9:48
 */
public interface LoginDao {

    void register(@Param("name") String name, @Param("phone") String phone, @Param("pswd") String encryptedPswd);

    User getUser(@Param("phone") String phone);
}
