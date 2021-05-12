package com.sealll.application.fav.bean;

import com.sealll.application.post.bean.Post;
import com.sealll.application.user.bean.User;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/11 21:10
 */
public class Fav {
    private User user;
    private List<Post> fav;
    private Integer count;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getFav() {
        return fav;
    }

    public void setFav(List<Post> fav) {
        this.fav = fav;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
