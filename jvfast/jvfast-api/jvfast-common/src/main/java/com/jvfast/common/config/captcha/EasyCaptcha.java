package com.jvfast.common.config.captcha;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.config.redis.RedisClient;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2020-01-07 20:52
 **/
public class EasyCaptcha implements Captcha {
    private static final String CAPTCHA_CACHE_KEY = "captcha:{}";

    @Autowired
    private RedisClient redisCache;
    @Autowired
    private JVFastCommonProperties jvFastCommonProperties;

    private int width;
    private int height;

    public EasyCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Map render() {
        HashMap<Object, Object> hashMap = Maps.newHashMapWithExpectedSize(2);
        String sessionId = IdUtil.fastSimpleUUID();
        hashMap.put("session", sessionId);
        // 获取验证码图片
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(width, height, 2);
        String code = arithmeticCaptcha.text();
        String pngBase64Str = arithmeticCaptcha.toBase64();
        hashMap.put("code", pngBase64Str);

        Integer expiredSeconds = jvFastCommonProperties.getCaptcha().getExpiredSeconds();
        String cacheKey = StrUtil.format(CAPTCHA_CACHE_KEY, sessionId);
        redisCache.setValue(cacheKey, code, Duration.ofSeconds(expiredSeconds));
        return hashMap;
    }

    @Override
    public String getCaptcha(String sessionId) {
        String cacheKey = StrUtil.format(CAPTCHA_CACHE_KEY, sessionId);
        String captchaValue = redisCache.getValue(cacheKey);
        // 验证码校验成功，删除Redis缓存
        if (null != captchaValue) {
            redisCache.deleteKey(cacheKey);
        }
        return captchaValue;
    }
}
