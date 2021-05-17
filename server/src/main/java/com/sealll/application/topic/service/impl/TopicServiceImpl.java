package com.sealll.application.topic.service.impl;

import com.github.pagehelper.PageHelper;
import com.sealll.application.post.bean.Post;
import com.sealll.application.topic.service.TopicService;
import com.sealll.constant.PageConstants;
import com.sealll.mapper.TopicMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 21:41
 */
@Service
@Async
@Transactional
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Override
    public List<Post> getPosts(String uid, Integer page, Long timestamp) {
        PageHelper.startPage(page, PageConstants.PAGESIZE);
        return topicMapper.getPosts(uid,timestamp);
    }

    @Override
    public boolean hasPost(String uid) throws JMSException {
        return false;
    }
}
