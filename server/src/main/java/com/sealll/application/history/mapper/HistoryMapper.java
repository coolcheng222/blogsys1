package com.sealll.application.history.mapper;

import com.sealll.application.post.bean.Post;

import java.util.List;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/15 18:22
 */
public interface HistoryMapper {
    public boolean addSearchHistory(String uid,String content);
    public boolean addPostHistory(String uid, String pid);

    public Set<String> getSearchHistory(String uid);
    public Set<String> getPostHistroy(String uid);
}
