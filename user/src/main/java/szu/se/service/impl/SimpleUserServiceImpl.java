package szu.se.service.impl;

import org.springframework.stereotype.Service;
import szu.se.dao.SimpleUser;
import szu.se.mapper.SimpleUserMapper;
import szu.se.service.SimpleUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.se.service.impl
 * @Author: Origami
 * @Date: 2023/10/2 10:16
 */
@Service
public class SimpleUserServiceImpl implements SimpleUserService {

    @Resource
    private SimpleUserMapper simpleUserMapper;

    @Override
    public List<SimpleUser> getSimpleUserList() {
        System.out.println("get simple_user list...");
        return simpleUserMapper.getSimpleUserList();
    }
}
