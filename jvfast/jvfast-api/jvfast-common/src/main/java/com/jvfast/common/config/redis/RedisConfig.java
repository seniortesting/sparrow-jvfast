package com.jvfast.common.config.redis;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jvfast.common.config.jackson.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Arrays;

@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {


    private static final int NEVER_EXPIRED_CACHE_SECONDS = -1;
    private static final int MIAO_SHA_CACHE_SECONDS = 3;
    // 过期时间是一天
    private static final int PERMISSIONS_CACHE_SECONDS = 24 * 60 * 60;

    public static final String CACHE_NEVER_EXPIRED = "storeCache";
    public static final String CACHE_MIAOSHA = "miaoshaCache";
    public static final String CACHE_PERMISSION = "permissionCache";


    @Bean
    public CacheProperties cacheProperties() {
        return new CacheProperties();
    }

    /**
     * {@link RedisCacheConfiguration}
     * 功能描述:  缓存产生的key的策略
     *
     * @Param:
     * @return:
     * @Author: Walter Hu
     * @Date: 2019/1/2 0002
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder redisKey = new StringBuilder();
            redisKey.append(method.getName());
            if (params.length > 0) {
                redisKey.append("-").append(Arrays.deepToString(params));
            }
            return redisKey.toString();
        };
    }

    @Bean
    @Primary
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializerEx stringRedisSerializer = new StringRedisSerializerEx();
        GenericJackson2JsonRedisSerializerEx genericJackson2JsonRedisSerializerEx = new GenericJackson2JsonRedisSerializerEx();

        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializerEx);

        // kv 序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializerEx);

        // hash 序列化
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializerEx);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic(IdWorker.get32UUID());
    }

    @Bean
    public PatternTopic patternTopic() {
        return new PatternTopic(IdWorker.get32UUID());
    }

    @Bean
    public RedisMessageListenerContainer messageListenerContainer(RedisConnectionFactory redisConnectionFactory, TaskExecutor taskExecutor) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.setSubscriptionExecutor(taskExecutor);
        redisMessageListenerContainer.setTaskExecutor(taskExecutor);
        // 设置监听器,注意这里有多少个listener就在这里添加这些listeners
//        redisMessageListenerContainer.addMessageListener(new PingListener(), channelTopic())
        return redisMessageListenerContainer;
    }

    /**
     * 申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
     * * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，将数据添加到缓存之中或者从缓存中移除某个值
     * 3分钟的redis cache
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @Primary
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration.determineConfiguration
        RedisCacheManager redisCacheManager = cacheSetting(redisConnectionFactory);
        return redisCacheManager;
    }

    /***
     *
     * @description: 从不过期的 redis cache
     *
     * @param: [redisConnectionFactory]
     * @return: org.springframework.data.redis.cache.RedisCacheManager
     * @author: Walter Hu
     * @date: 2019/1/22 0022
     */
    @Bean(name = CACHE_NEVER_EXPIRED)
    public RedisCacheManager neverCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager redisCacheManager = cacheSetting(redisConnectionFactory, NEVER_EXPIRED_CACHE_SECONDS);
        return redisCacheManager;
    }

    /***
     *
     * 缓存全局配置参数
     *
     * @param: [redisConnectionFactory, seconds]
     * @return: org.springframework.data.redis.cache.RedisCacheManager
     * @author: Walter Hu
     * @date: 2019/5/17 0017
     */
    private RedisCacheManager cacheSetting(RedisConnectionFactory redisConnectionFactory, int... seconds) {
        CacheProperties.Redis redis = cacheProperties().getRedis();

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(JacksonUtil.getInstance());

        // 设置默认缓存的时间
        String keyPrefix = redis.getKeyPrefix();
        Duration timeToLive = redis.getTimeToLive();
        if (seconds.length > 0) {
            timeToLive = Duration.ofSeconds(seconds[0]);
        }
        final RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive)
//                .disableCachingNullValues()
                .prefixKeysWith(keyPrefix)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        final RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .transactionAware()
                .cacheDefaults(cacheConfiguration).build();
        return redisCacheManager;
    }

    /**
     * 功能描述: 捕获redis连接或者存放cache时候遇到的异常情况
     *
     * @Param: []
     * @return: org.springframework.cache.com.jvfast.common.config.interceptor.CacheErrorHandler
     * @Author: Walter Hu
     * @Date: 2019/1/5 0005
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }

    @Slf4j
    public static class RedisCacheErrorHandler implements CacheErrorHandler {

        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
            log.error("Unable to get from cache " + cache.getName() + " : " + exception.getMessage());
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
            log.error("Unable to put into cache " + cache.getName() + " : " + exception.getMessage());
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
            log.error("Unable to evict from cache " + cache.getName() + " : " + exception.getMessage());
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
            log.error("Unable to clean cache " + cache.getName() + " : " + exception.getMessage());
        }
    }
}
