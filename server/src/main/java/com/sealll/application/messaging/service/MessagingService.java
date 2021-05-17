package com.sealll.application.messaging.service;

import com.sealll.application.message.bean.Message2;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 10:44
 */
public interface MessagingService {
    Integer getCount(String uid);

    List<Message2> getUnread(String uid);
}

