package com.jvfast.common.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 公用的redis服务接口
 * @project: vrpanoservice
 * @author: Walter Hu
 * @create: 2019-01-19 23:37
 **/
@Service
@Slf4j
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CacheProperties cacheProperties;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 是否存在改值
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        return redisTemplate.hasKey(storedKey);
    }

    /**
     * 添加缓存,永远不过期
     *
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void setValue(String key, T t) {
        setValue(key, t, null);
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param t
     * @param duration
     * @param <T>
     */
    public <T> void setValue(String key, T t, Duration duration) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        if (duration != null) {
            valueOperations.set(storedKey, t, duration);
        } else {
            valueOperations.set(storedKey, t);
        }
    }

    public <T> void setLogValue(String key, T t) {
        HyperLogLogOperations hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        hyperLogLogOperations.add(storedKey, t);
    }

    /***
     *
     * @description: 查询对应的redis cache
     *
     * @param: [cacheName, key, t]
     * @return: T
     * @author: Walter Hu
     * @date: 2019/1/23 0023
     */
    public <T> T getValue(String key) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        Boolean hasKey = hasKey(key);
        if (hasKey != null && hasKey) {
            T hashData = valueOperations.get(storedKey);
            return hashData;
        }
        return null;
    }

    /**
     * https://www.toutiao.com/i6704059442654085636/
     * https://www.nowcoder.com/discuss/37157?type=0&order=0&pos=15&page=1
     * https://blog.csdn.net/zuochao_2013/article/details/82779124
     * 高并发请求缓存设计  -- 读写分离设计
     *
     * @param key
     * @param duration
     * @param <T>      ,Callable对象或者写成一个lamba表达式
     * @return 缓存或者数据库的数据
     */
    public <T> T getValue(String key, Duration duration, Callable<T> dbLoader) {
        T value = null;
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        // 先上一把读锁
        readWriteLock.readLock().lock();
        try {
            Boolean hasKey = hasKey(key);
            String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
            if (hasKey) {
                return valueOperations.get(storedKey);
            }
            // 如果数据为空，则需要到数据库中查询数据，所以这时候把读锁释放掉，保证读锁可以并发操作，
            // 此时上一把写锁，不能同时写数据
            readWriteLock.readLock().unlock();
            readWriteLock.writeLock().lock();
            try {
                //必须重新检查value是否为空，因为这时候，另外一个线程可能会获得写锁，从而让obj有值
                if (value == null) {
                    try {
                        value = dbLoader.call();
                        // 保存缓存数据
                        if (value != null) {
                            setValue(key, value, duration);
                        } else {
                            // 如果是null值，设置60秒过期时间
                            setValue(key, "", Duration.ofSeconds(60L));
                        }
                    } catch (Exception e) {
                        throw new Cache.ValueRetrievalException(storedKey, dbLoader, e);
                    }
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }
            //因为前面释放了写锁，所以这里要把读锁重新锁上
            readWriteLock.readLock().lock();
        } finally {
            // 修改上面的throw new Cache.ValueRetrievalException 导致没有加读锁
            int readLockCount = readWriteLock.getReadLockCount();
            if (readLockCount > 0) {
                readWriteLock.readLock().unlock();
            }
        }
        return value;
    }

    /**
     * 删除key
     *
     * @param key
     */
    public Boolean deleteKey(String key) {
        Boolean hasKey = hasKey(key);
        if (hasKey) {
            String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
            return redisTemplate.delete(storedKey);
        }
        return true;
    }

    /**
     * 增加值
     *
     * @param key
     * @param startValue
     * @return
     */
    public Long increment(String key, Long startValue) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        Long currentValue = startValue != null ? valueOperations.increment(storedKey, startValue) : valueOperations.increment(storedKey);
        return currentValue;
    }

    /**
     * 减少值
     *
     * @param key
     * @param startValue
     * @return
     */
    public Long decrement(String key, Long startValue) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        Long currentValue = startValue != null ? valueOperations.decrement(storedKey, startValue) : valueOperations.decrement(storedKey);
        return currentValue;
    }

    /**
     * 增加值
     *
     * @param key
     * @param startValue
     * @return
     */
    public Double increment(String key, Double startValue) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String storedKey = cacheProperties.getRedis().getKeyPrefix() + key;
        Double currentValue = startValue != null ? valueOperations.increment(storedKey, startValue) : valueOperations.increment(storedKey);
        return currentValue;
    }


    /**
     * 发送redis消息
     *
     * @param channel
     * @param message
     */
    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
