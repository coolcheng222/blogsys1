package com.sealll.application.topic.service;

import com.sealll.application.post.bean.Post;
import org.apache.ibatis.annotations.Param;

import javax.jms.JMSException;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 21:41
 */
public interface TopicService {
    public List<Post> getPosts(String uid, Integer page,Long timestamp);
    public boolean hasPost(String uid) throws JMSException;
}
