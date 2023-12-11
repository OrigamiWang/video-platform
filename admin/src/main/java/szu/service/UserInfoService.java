package szu.service;

import org.apache.ibatis.annotations.Select;
import szu.vo.UserInfo;

public interface UserInfoService {

    UserInfo getUserInfoByUid(int uid);
    @Select("select id from `user` where `name` = #{upName}}")
    Integer getUidByName(String upName);
}
