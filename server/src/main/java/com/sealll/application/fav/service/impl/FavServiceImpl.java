package com.sealll.application.fav.service.impl;

import com.sealll.application.fav.bean.Fav;
import com.sealll.application.fav.bean.Faved;
import com.sealll.application.fav.bean.Faving;
import com.sealll.application.fav.service.FavService;
import com.sealll.mapper.FavMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/13 11:41
 */
@Service
@Transactional
@Async
public class FavServiceImpl implements FavService {
    @Autowired
    private FavMapper favMapper;

    @Override
    public boolean addFav(String uid, String pid) {
        boolean b = favMapper.addFav(uid, pid);
        return b;
    }

    @Override
    public boolean deleteFav(String uid, String pid) {
        return favMapper.deleteFav(uid,pid);
    }

    @Override
    public Fav getUserFav(String uid) {
        return favMapper.getUserFav(uid);
    }

    @Override
    public Fav getUserFavCount(String uid) {
        return favMapper.getUserFavCount(uid);
    }

    @Override
    public Faving checkFav(String uid, String pid) {
        try{
            return favMapper.checkFav(uid, Arrays.asList(pid)).get(pid);

        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public Faved getPostFaved(String uid) {
        return favMapper.getPostFaved(uid);

    }

    @Override
    public Faved getPostFavedCount(String pid) {
        return favMapper.getPostFavedCount(pid);
    }

    @Override
    public List<Faved> getPostFavedCountList() {
        return favMapper.getPostFavedCountList();
    }
}
