package com.sealll.application.sub.service.impl;

import com.sealll.application.sub.bean.Subscribe;
import com.sealll.application.sub.service.SubscribeService;
import com.sealll.mapper.SubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sealll
 * @time 2021/5/16 13:06
 */
@Service
@Async
@Transactional
public class SubscribeServiceImpl implements SubscribeService {
    @Autowired
    private SubscribeMapper subscribeMapper;
    @Override
    public boolean addSub(String subber, String subbed) {
        boolean b = subscribeMapper.addSub(subber, subbed);
        return b;
    }

    @Override
    public boolean deleteSub(String subber, String subbed) {
        boolean b = subscribeMapper.deleteSub(subber, subbed);
        return b;
    }

    @Override
    public boolean checkSub(String subber, String subbed) {
        boolean b = subscribeMapper.checkSub(subber, subbed);
        return b;
    }

    @Override
    public Subscribe getSubing(String uid) {
        Subscribe subing = subscribeMapper.getSubing(uid);
        return subing;
    }

    @Override
    public Subscribe getFans(String uid) {
        Subscribe fans = subscribeMapper.getFans(uid);
        return fans;
    }
}
