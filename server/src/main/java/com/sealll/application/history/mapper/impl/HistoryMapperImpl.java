package com.sealll.application.history.mapper.impl;

import com.sealll.application.history.mapper.HistoryMapper;
import com.sealll.constant.PageConstants;
import com.sealll.constant.ParameterConstants;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import sun.jvm.hotspot.debugger.Page;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/15 18:48
 */
@Repository
public class HistoryMapperImpl implements HistoryMapper {
    @Autowired
    private JedisPool jedisPool;

    @Override
    public boolean addSearchHistory(String uid, String content) {
        Jedis resource = jedisPool.getResource();
        Pipeline pipelined = resource.pipelined();
        Response<Long> zadd = pipelined.zadd(ParameterConstants.HISTORYSEARCH + uid, new Date().getTime() + PageConstants.TTL, content);
        pipelined.zremrangeByScore(ParameterConstants.HISTORYSEARCH + uid,0,new Date().getTime());
        pipelined.zremrangeByRank(ParameterConstants.HISTORYSEARCH + uid, PageConstants.SEARCHHISTORY,-1);
        pipelined.sync();
        Long aLong = zadd.get();
        return aLong != 0;
    }

    @Override
    public boolean addPostHistory(String uid, String pid) {
        Jedis resource = jedisPool.getResource();
        Pipeline pipelined = resource.pipelined();
        Response<Long> zadd = pipelined.zadd(ParameterConstants.HISTORYREAD + uid, new Date().getTime() + PageConstants.TTL, pid);
        pipelined.zremrangeByScore(ParameterConstants.HISTORYREAD + uid,0,new Date().getTime());
        pipelined.zremrangeByRank(ParameterConstants.HISTORYREAD + uid, PageConstants.POSTHISTORY,-1);
        pipelined.sync();
        Long aLong = zadd.get();
        return aLong != 0;
    }

    @Override
    public Set<String> getSearchHistory(String uid) {
        Jedis resource = jedisPool.getResource();
        Pipeline pipelined = resource.pipelined();
        pipelined.zremrangeByScore(ParameterConstants.HISTORYSEARCH + uid,0,new Date().getTime());
        Response<Set<String>> zrange = pipelined.zrange(ParameterConstants.HISTORYSEARCH + uid, 0, PageConstants.SEARCHHISTORY);
        pipelined.sync();
        Set<String> strings = zrange.get();
        return strings;
    }

    @Override
    public Set<String> getPostHistroy(String uid) {
        Jedis resource = jedisPool.getResource();
        Pipeline pipelined = resource.pipelined();
        pipelined.zremrangeByScore(ParameterConstants.HISTORYREAD+ uid,0,new Date().getTime());
        Response<Set<String>> zrange = pipelined.zrange(ParameterConstants.HISTORYREAD + uid, 0, PageConstants.POSTHISTORY);
        pipelined.sync();
        Set<String> strings = zrange.get();
        return strings;
    }
}
