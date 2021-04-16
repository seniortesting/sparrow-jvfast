package com.jvfast.wx.open.config;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jvfast.wx.open")
public class WxOpenProperties {

    /**
     * 平台名称
     */
    private String name;
    /**
     * 设置微信三方平台的appid
     */
    private String appId;

    /**
     * 设置微信三方平台的app secret
     */
    private String secret;

    /**
     * 设置微信三方平台的token
     */
    private String token;

    /**
     * 设置微信三方平台的EncodingAESKey
     */
    private String aesKey;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
