package com.jvfast.wx.mp.config;

import lombok.Data;
import me.chanjar.weixin.common.enums.TicketType;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@Data
public class CustomWxMpRedisConfigImpl extends WxMpDefaultConfigImpl {
    private static final String ACCESS_TOKEN_KEY_TPL = "%s:access_token:%s";
    private static final String TICKET_KEY_TPL = "%s:ticket:key:%s:%s";
    private static final String LOCK_KEY_TPL = "%s:lock:%s:";

    private final WxRedisOps redisOps;
    private final String keyPrefix;

    private String accessTokenKey;
    private String lockKey;

    public CustomWxMpRedisConfigImpl(StringRedisTemplate stringRedisTemplate) {
        this(new RedisTemplateWxRedisOps(stringRedisTemplate), "wx");
    }

    public CustomWxMpRedisConfigImpl(WxRedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }

    /**
     * 每个公众号生成独有的存储key.
     */
    @Override
    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = String.format(ACCESS_TOKEN_KEY_TPL, this.keyPrefix, appId);
        this.lockKey = String.format(LOCK_KEY_TPL, this.keyPrefix, appId);
        accessTokenLock = this.redisOps.getLock(lockKey.concat("accessTokenLock"));
        jsapiTicketLock = this.redisOps.getLock(lockKey.concat("jsapiTicketLock"));
        sdkTicketLock = this.redisOps.getLock(lockKey.concat("sdkTicketLock"));
        cardApiTicketLock = this.redisOps.getLock(lockKey.concat("cardApiTicketLock"));
    }

    private String getTicketRedisKey(TicketType type) {
        return String.format(TICKET_KEY_TPL, this.keyPrefix, appId, type.getCode());
    }

    @Override
    public String getAccessToken() {
        return redisOps.getValue(this.accessTokenKey);
    }

    @Override
    public boolean isAccessTokenExpired() {
        Long expire = redisOps.getExpire(this.accessTokenKey);
        return expire == null || expire < 2;
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        redisOps.setValue(this.accessTokenKey, accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
    }

    @Override
    public void expireAccessToken() {
        redisOps.expire(this.accessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public String getTicket(TicketType type) {
        return redisOps.getValue(this.getTicketRedisKey(type));
    }

    @Override
    public boolean isTicketExpired(TicketType type) {
        return redisOps.getExpire(this.getTicketRedisKey(type)) < 2;
    }

    @Override
    public synchronized void updateTicket(TicketType type, String jsapiTicket, int expiresInSeconds) {
        redisOps.setValue(this.getTicketRedisKey(type), jsapiTicket, expiresInSeconds - 200, TimeUnit.SECONDS);
    }

    @Override
    public void expireTicket(TicketType type) {
        redisOps.expire(this.getTicketRedisKey(type), 0, TimeUnit.SECONDS);
    }

}
