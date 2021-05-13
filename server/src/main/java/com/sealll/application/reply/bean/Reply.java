package com.sealll.application.reply.bean;

import com.sealll.application.post.bean.Post;
import com.sealll.application.user.bean.User;

import java.util.Date;

/**
 * @author sealll
 * @time 2021/5/13 16:09
 */
public class Reply {
    private String reid;
    private Post post;
    private User user;
    private String content;
    private Date time;
    private Integer stars;
    private Boolean stared;

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
