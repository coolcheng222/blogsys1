package com.sealll.application.messaging.service;

import com.sealll.application.message.bean.Message2;

import javax.jms.JMSException;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 10:44
 */
public interface MessagingService {
    Integer getCount(String uid) throws JMSException;

    List<Message2> getUnread(String uid) throws JMSException;
}

