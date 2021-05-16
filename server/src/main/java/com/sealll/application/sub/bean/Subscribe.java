package com.sealll.application.sub.bean;

import com.sealll.application.userinfo.bean.UserInfo;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/14 10:29
 */
public class Subscribe {
    private String uid;
    private List<UserInfo> userInfos;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
}
