package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.dao.LoginDao;
import szu.dto.RegisterDto;
import szu.service.LoginService;

import javax.annotation.Resource;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service.impl
 * @Author: Origami
 * @Date: 2023/10/29 10:09
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;


    @Override
    public void register(RegisterDto registerDto) {
        // encrypt the pswd
        String pswd = registerDto.getPswd();
        String encryptedPswd = pswd;
        loginDao.register(registerDto.getName(), registerDto.getPhone(), encryptedPswd);
    }
}
