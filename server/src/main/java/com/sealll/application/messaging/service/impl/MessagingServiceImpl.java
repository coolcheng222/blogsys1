package com.sealll.application.messaging.service.impl;

import com.sealll.application.message.bean.Message2;
import com.sealll.application.messaging.service.MessagingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 10:44
 */
@Service
@Async
@Transactional
public class MessagingServiceImpl implements MessagingService {

    @Override
    public Integer getCount(String uid) {
        return null;
    }

    @Override
    public List<Message2> getUnread(String uid) {
        return null;
    }
}
