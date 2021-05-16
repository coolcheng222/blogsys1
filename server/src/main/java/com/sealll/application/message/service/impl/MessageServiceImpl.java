package com.sealll.application.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.sealll.application.message.bean.Message2;
import com.sealll.application.message.bean.Message2Example;
import com.sealll.application.message.service.MessageService;
import com.sealll.constant.PageConstants;
import com.sealll.mapper.Message2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/16 18:49
 */
@Service
@Async
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private Message2Mapper message2Mapper;
    @Override
    public List<Message2> getMessageBeforeTime(String uid, Long time, Integer page) {
        Message2Example message2Example = new Message2Example();
        Message2Example.Criteria criteria = message2Example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andTimeLessThan(new Date(time));
        message2Example.setOrderByClause("time desc");
        PageHelper.startPage(page, PageConstants.PAGESIZE);
        List<Message2> message2s = message2Mapper.selectByExample(message2Example);
        return message2s;
    }

    @Override
    public Message2 getByMid(String mid) {
        Message2Example message2Example = new Message2Example();
        message2Example.createCriteria().andMIdEqualTo(mid);
        List<Message2> message2s = message2Mapper.selectByExample(message2Example);
        if(message2s != null){
            return message2s.get(0);
        }else{
            return null;
        }
    }

    @Override
    public boolean addMessage(Message2 message) {
        return message2Mapper.insert(message) != 0;
    }

    @Override
    public boolean deleteMessage(String mId) {
        Message2Example ex = new Message2Example();
        Message2Example.Criteria criteria = ex.createCriteria().andMIdEqualTo(mId);
        int i = message2Mapper.deleteByExample(ex);
        return i != 0;
    }
}
