package com.sealll.application.star.service.impl;

import com.sealll.application.star.bean.Stared;
import com.sealll.application.star.service.StaredService;
import com.sealll.mapper.StaredMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sealll
 * @time 2021/5/13 21:01
 */
@Service
@Async
@Transactional
public class StaredServiceImpl implements StaredService {
    @Autowired
    private StaredMapper staredMapper;
    @Override
    public boolean addStar(Stared stared) {
        return staredMapper.addStar(stared);
    }

    @Override
    public boolean deleteStar(Stared stared) {
        return staredMapper.deleteStar(stared);
    }
}
