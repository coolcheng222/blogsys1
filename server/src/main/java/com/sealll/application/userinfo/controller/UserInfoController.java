package com.sealll.application.userinfo.controller;

import com.sealll.application.userinfo.bean.UserInfo;
import com.sealll.application.userinfo.service.UserInfoService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author sealll
 * @time 2021/5/16 12:16
 */
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @GetMapping("/userinfo/{uid}")
    public Msg getUserInfo(@PathVariable("uid") String uid){
        UserInfo userInfo = userInfoService.getUserInfo(uid);
        return ResultHandler.handle(userInfo, Objects::isNull,"没有用户",userInfo);
    }
}
