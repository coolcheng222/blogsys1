package com.sealll.shiro.redis;

import com.sealll.constant.ParameterConstants;
import org.apache.shiro.util.ThreadContext;
import org.crazycake.shiro.RedisManager;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/4/25 15:40
 */
@Deprecated
public class PipelineRedisManager extends RedisManager {


    @Override
    protected Jedis getJedis() {
        if (getJedisPool() == null) {
            synchronized (RedisManager.class) {
                if (getJedisPool() == null) {
                    String[] hostAndPort = getHost().split(":");
                    setJedisPool(new JedisPool(getJedisPoolConfig(), hostAndPort[0], Integer.parseInt(hostAndPort[1]), getTimeout(), getPassword(), getDatabase()));
                }
            }
        }
        Jedis jedis = (Jedis) ThreadContext.get(ParameterConstants.JEDIS);
        if (jedis == null) {
            Jedis resource = getJedisPool().getResource();
            Jedis resource2 = getJedisPool().getResource();
            ThreadContext.put(ParameterConstants.JEDIS, resource);
            ThreadContext.put(ParameterConstants.PIPELINE, resource2.pipelined());
            return resource;
        } else {
            return jedis;
        }
    }

    @Override
    public byte[] get(byte[] key) {
        if (key == null) {
            return null;
        }
        byte[] value;
        Jedis jedis = getJedis();

        value = jedis.get(key);
        return value;
    }

    /**
     * set
     *
     * @param key        key
     * @param value      value
     * @param expireTime expire time in second
     * @return value
     */
    @Override
    public byte[] set(byte[] key, byte[] value, int expireTime) {
        if (key == null) {
            return null;
        }
        getJedis();
        Pipeline pipeline = (Pipeline) ThreadContext.get(ParameterConstants.PIPELINE);
        // -1 and 0 is not a valid expire time in Jedis
        if (expireTime > 0) {
            pipeline.expire(key, expireTime);
        }

        return value;
    }

    /**
     * Delete a key-value pair.
     *
     * @param key key
     */
    @Override
    public void del(byte[] key) {
        if (key == null) {
            return;
        }
        getJedis();
        Pipeline pipeline = (Pipeline) ThreadContext.get(ParameterConstants.PIPELINE);
        pipeline.del(key);

    }

    /**
     * Return the size of redis db.
     *
     * @param pattern key pattern
     * @return key-value size
     */
    @Override
    public Long dbSize(byte[] pattern) {
        long dbSize = 0L;
        Jedis jedis = getJedis();

        ScanParams params = new ScanParams();
        params.count(getCount());
        params.match(pattern);
        byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;
        ScanResult<byte[]> scanResult;
        do {
            scanResult = jedis.scan(cursor, params);
            List<byte[]> results = scanResult.getResult();
            for (byte[] result : results) {
                dbSize++;
            }
            cursor = scanResult.getCursorAsBytes();
        } while (scanResult.getCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);

        return dbSize;
    }

    /**
     * Return all the keys of Redis db. Filtered by pattern.
     *
     * @param pattern key pattern
     * @return key set
     */
    public Set<byte[]> keys(byte[] pattern) {
        Set<byte[]> keys = new HashSet<byte[]>();
        Jedis jedis = getJedis();


        ScanParams params = new ScanParams();
        params.count(getCount());
        params.match(pattern);
        byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;
        ScanResult<byte[]> scanResult;
        do {
            scanResult = jedis.scan(cursor, params);
            keys.addAll(scanResult.getResult());
            cursor = scanResult.getCursorAsBytes();
        } while (scanResult.getCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);

        return keys;

    }
}
