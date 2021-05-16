package com.sealll.mapper;

import com.sealll.application.userinfo.bean.UserInfo;

/**
 * @author sealll
 * @time 2021/5/16 11:56
 */
public interface UserInfoMapper {
    public UserInfo getUserInfo(String uid);
}
