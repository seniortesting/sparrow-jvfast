package com.jvfast.wx.open.config;

import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WxOpenRedisStorageImpl extends WxOpenInMemoryConfigStorage {
    private final static String COMPONENT_VERIFY_TICKET_KEY = "wechat_component_verify_ticket:";
    private final static String COMPONENT_ACCESS_TOKEN_KEY = "wechat_component_access_token:";

    private final static String AUTHORIZER_REFRESH_TOKEN_KEY = "wechat_authorizer_refresh_token:";
    private final static String AUTHORIZER_ACCESS_TOKEN_KEY = "wechat_authorizer_access_token:";
    private final static String JSAPI_TICKET_KEY = "wechat_jsapi_ticket:";
    private final static String CARD_API_TICKET_KEY = "wechat_card_api_ticket:";

    protected final RedisTemplate redisTemplate;
    /**
     * redis 存储的 key 的前缀，可为空
     */
    private String keyPrefix;
    private String componentVerifyTicketKey;
    private String componentAccessTokenKey;
    private String authorizerRefreshTokenKey;
    private String authorizerAccessTokenKey;
    private String jsapiTicketKey;
    private String cardApiTicket;

    public WxOpenRedisStorageImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public WxOpenRedisStorageImpl(RedisTemplate redisTemplate, String keyPrefix) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = keyPrefix;
    }

    @Override
    public void setComponentAppId(String componentAppId) {
        super.setComponentAppId(componentAppId);
        String prefix = StringUtils.isBlank(keyPrefix) ? "" :
                (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
        componentVerifyTicketKey = prefix + COMPONENT_VERIFY_TICKET_KEY.concat(componentAppId);
        componentAccessTokenKey = prefix + COMPONENT_ACCESS_TOKEN_KEY.concat(componentAppId);
        authorizerRefreshTokenKey = prefix + AUTHORIZER_REFRESH_TOKEN_KEY.concat(componentAppId);
        authorizerAccessTokenKey = prefix + AUTHORIZER_ACCESS_TOKEN_KEY.concat(componentAppId);
        this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(componentAppId);
        this.cardApiTicket = CARD_API_TICKET_KEY.concat(componentAppId);
    }

    @Override
    public String getComponentVerifyTicket() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object ticket = valueOperations.get(this.componentVerifyTicketKey);
        if (ticket != null) {
            return (String) ticket;
        }
        return null;
    }

    @Override
    public void setComponentVerifyTicket(String componentVerifyTicket) {
        redisTemplate.opsForValue().set(this.componentVerifyTicketKey, componentVerifyTicket);
    }

    @Override
    public String getComponentAccessToken() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object accessToken = valueOperations.get(this.componentAccessTokenKey);
        if (accessToken != null) {
            return (String) accessToken;
        }
        return null;
    }

    @Override
    public boolean isComponentAccessTokenExpired() {
        boolean expired = redisTemplate.getExpire(this.componentAccessTokenKey) < 2;
        return expired;
    }

    @Override
    public void expireComponentAccessToken() {
        redisTemplate.expire(this.componentAccessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateComponentAccessToken(String componentAccessToken, int expiresInSeconds) {
        redisTemplate.opsForValue().set(this.componentAccessTokenKey, componentAccessToken, Duration.ofSeconds(expiresInSeconds - 200));
    }

    private String getKey(String prefix, String appId) {
        return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
    }

    @Override
    public String getAuthorizerRefreshToken(String appId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object RefreshToken = valueOperations.get(this.getKey(this.authorizerRefreshTokenKey, appId));
        if (RefreshToken != null) {
            return (String) RefreshToken;
        }
        return null;
    }

    @Override
    public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
        redisTemplate.opsForValue().set(this.getKey(this.authorizerRefreshTokenKey, appId), authorizerRefreshToken);
    }

    @Override
    public String getAuthorizerAccessToken(String appId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object AccessToken = valueOperations.get(this.getKey(this.authorizerAccessTokenKey, appId));
        if (AccessToken != null) {
            return (String) AccessToken;
        }
        return null;
    }

    @Override
    public boolean isAuthorizerAccessTokenExpired(String appId) {
        boolean expired = redisTemplate.getExpire(this.getKey(this.authorizerAccessTokenKey, appId)) < 2;
        return expired;
    }

    @Override
    public void expireAuthorizerAccessToken(String appId) {
        redisTemplate.expire(this.getKey(this.authorizerAccessTokenKey, appId), 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken, int expiresInSeconds) {
        redisTemplate.opsForValue().set(this.getKey(this.authorizerAccessTokenKey, appId), authorizerAccessToken, Duration.ofSeconds(expiresInSeconds - 200));
    }

    @Override
    public String getJsapiTicket(String appId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object jsapiTicket = valueOperations.get(this.getKey(this.jsapiTicketKey, appId));
        if (jsapiTicket != null) {
            return (String) jsapiTicket;
        }
        return null;
    }

    @Override
    public boolean isJsapiTicketExpired(String appId) {
        boolean expired = redisTemplate.getExpire(this.getKey(this.jsapiTicketKey, appId)) < 2;
        return expired;
    }

    @Override
    public void expireJsapiTicket(String appId) {
        redisTemplate.expire(this.getKey(this.jsapiTicketKey, appId), 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateJsapiTicket(String appId, String jsapiTicket, int expiresInSeconds) {
        redisTemplate.opsForValue().set(this.getKey(this.jsapiTicketKey, appId), jsapiTicket, Duration.ofSeconds(expiresInSeconds - 200));
    }

    @Override
    public String getCardApiTicket(String appId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object cardApiTicket = valueOperations.get(this.getKey(this.cardApiTicket, appId));
        if (cardApiTicket != null) {
            return (String) cardApiTicket;
        }
        return null;
    }

    @Override
    public boolean isCardApiTicketExpired(String appId) {
        boolean expired = redisTemplate.getExpire(this.getKey(this.cardApiTicket, appId)) < 2;
        return expired;
    }

    @Override
    public void expireCardApiTicket(String appId) {
        redisTemplate.expire(this.getKey(this.cardApiTicket, appId), 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateCardApiTicket(String appId, String cardApiTicket, int expiresInSeconds) {
        redisTemplate.opsForValue().set(this.getKey(this.cardApiTicket, appId), cardApiTicket, Duration.ofSeconds(expiresInSeconds - 200));
    }
}
