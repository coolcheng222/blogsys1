package com.sealll.application.userinfo.bean;

/**
 * @author sealll
 * @time 2021/5/14 10:24
 */
public class UserInfo {
    private String uid;
    private String username;
    private String rolename;
    private Integer posts;
    private Integer subing;
    private Integer subbed;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    public Integer getSubing() {
        return subing;
    }

    public void setSubing(Integer subing) {
        this.subing = subing;
    }

    public Integer getSubbed() {
        return subbed;
    }

    public void setSubbed(Integer subbed) {
        this.subbed = subbed;
    }
}
