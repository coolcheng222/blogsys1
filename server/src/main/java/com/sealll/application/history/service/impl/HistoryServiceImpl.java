package com.sealll.application.history.service.impl;

import com.sealll.application.history.mapper.HistoryMapper;
import com.sealll.application.history.service.HistoryService;
import com.sealll.application.post.bean.Post;
import com.sealll.application.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/15 20:00
 */
@Service
@Async
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private PostService postService;
    @Override
    public boolean addSearchHistory(String uid, String content) {
        boolean b = historyMapper.addSearchHistory(uid, content);
        return b;
    }

    @Override
    public boolean addPostHistory(String uid, String pid) {
        boolean b = historyMapper.addPostHistory(uid, pid);
        return b;
    }

    @Override
    public Set<String> getSearchHistory(String uid) {
        Set<String> searchHistory = historyMapper.getSearchHistory(uid);
        return searchHistory;
    }

    @Override
    public List<Post> getPostHistroy(String uid) {
        Set<String> postHistroy = historyMapper.getPostHistroy(uid);
        if(postHistroy == null){
            return null;
        }else{
            return postService.getPostByPids(new ArrayList<>(postHistroy));
        }
    }
}
