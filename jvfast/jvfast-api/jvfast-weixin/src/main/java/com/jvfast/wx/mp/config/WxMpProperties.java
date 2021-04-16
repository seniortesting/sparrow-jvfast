package com.jvfast.wx.mp.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "jvfast.wx.mp")
public class WxMpProperties {

    private List<MpConfig> configs = Lists.newArrayList();

    @Data
    public static class MpConfig {

        /**
         * 微信公众号名称
         */
        private String name;
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;

        private String url;
    }
}
