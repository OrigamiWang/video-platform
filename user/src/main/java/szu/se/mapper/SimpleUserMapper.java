package szu.se.mapper;

import szu.se.dao.SimpleUser;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.se.mapper
 * @Author: Origami
 * @Date: 2023/10/2 10:03
 */
public interface SimpleUserMapper {
    List<SimpleUser> getSimpleUserList();
}
