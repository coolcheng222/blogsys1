package com.sealll.application.history.service;

import com.sealll.application.post.bean.Post;

import java.util.List;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/15 19:59
 */
public interface HistoryService {
    public boolean addSearchHistory(String uid,String content);
    public boolean addPostHistory(String uid, String pid);

    public Set<String> getSearchHistory(String uid);
    public List<Post> getPostHistroy(String uid);
}
