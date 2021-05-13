package com.sealll.application.fav.bean;

import com.sealll.application.post.bean.Post;
import com.sealll.application.user.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/11 21:12
 */
public class Faved {
    private String pid;
    private List<User> users = new ArrayList<>();
    private Integer count;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
