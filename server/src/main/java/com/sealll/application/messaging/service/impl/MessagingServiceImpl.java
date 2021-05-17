package com.sealll.application.messaging.service.impl;

import com.sealll.application.message.bean.Message2;
import com.sealll.application.messaging.mapper.MessagingMapper;
import com.sealll.application.messaging.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 10:44
 */
@Service
@Async
@Transactional
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private MessagingMapper messagingMapper;
    @Override
    public Integer getCount(String uid) throws JMSException {
        Integer count = messagingMapper.getCount(uid);
        return count;
    }

    @Override
    public List<Message2> getUnread(String uid) throws JMSException {
        List<Message2> list =  messagingMapper.getUnread(uid);
        return list;
    }
}
