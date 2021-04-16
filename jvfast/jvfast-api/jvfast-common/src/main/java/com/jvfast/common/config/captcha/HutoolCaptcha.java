/**
 * Copyright © 2018 TaoYu (tracy5546@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jvfast.common.config.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.config.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 谷歌默认验证码组件
 *
 * @author TaoYu
 */
@Slf4j
public class HutoolCaptcha implements Captcha {

    public static final String BASE64_IMAGE_PREFIX = "data:image/png;base64,";
    private static final String CAPTCHA_CACHE_KEY = "captcha:{}";

    @Autowired
    private RedisClient redisCache;
    @Autowired
    private JVFastCommonProperties jvFastCommonProperties;


    private int width;
    private int height;

    public HutoolCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Map render() {
        try {
            HashMap<Object, Object> hashMap = Maps.newHashMapWithExpectedSize(2);
            String sessionId = IdUtil.fastSimpleUUID();
            hashMap.put("session", sessionId);
            // 创建验证码
            File captchaFile = FileUtil.createTempFile("captcha", ".png", null, true);
            ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(width, height);
            shearCaptcha.createCode();
            shearCaptcha.write(captchaFile);
            String code = shearCaptcha.getCode();
            String imageBase64 = shearCaptcha.getImageBase64();
            // 保存到对应的redis数据库睡死
            String pngBase64Str = BASE64_IMAGE_PREFIX + imageBase64;

            hashMap.put("code", pngBase64Str);
            Integer expiredSeconds = jvFastCommonProperties.getCaptcha().getExpiredSeconds();
            String cacheKey = StrUtil.format(CAPTCHA_CACHE_KEY, sessionId);
            redisCache.setValue(cacheKey, code, Duration.ofSeconds(expiredSeconds));
            return hashMap;
        } catch (Exception e) {
            throw new CaptchaException(e);
        }
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

