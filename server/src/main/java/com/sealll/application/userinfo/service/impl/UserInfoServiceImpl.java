package com.sealll.application.userinfo.service.impl;

import com.sealll.application.userinfo.bean.UserInfo;
import com.sealll.application.userinfo.service.UserInfoService;
import com.sealll.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sealll
 * @time 2021/5/16 12:15
 */
@Service
@Async
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo getUserInfo(String uid) {
        return userInfoMapper.getUserInfo(uid);
    }
}
