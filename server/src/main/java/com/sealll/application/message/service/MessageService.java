package com.sealll.application.message.service;

import com.sealll.application.message.bean.Message2;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/16 18:40
 */
public interface MessageService {
    public List<Message2> getMessageBeforeTime(String uid,Long time,Integer page);
    public Message2 getByMid(String mid);
    public boolean addMessage(Message2 message);
    public boolean deleteMessage(String mId);
}
