package com.jvfast.wx.pay.config;

import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
    private final WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        if (StrUtil.isNotEmpty(properties.getAppId())
                && StrUtil.isNotEmpty(properties.getMchId())
                && StrUtil.isNotEmpty(properties.getMchKey())
                && StrUtil.isNotEmpty(properties.getSubAppId())
                && StrUtil.isNotEmpty(properties.getSubMchId())
                && StrUtil.isNotEmpty(properties.getKeyPath())) {
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(StrUtil.trimToNull(properties.getAppId()));
            payConfig.setMchId(StrUtil.trimToNull(properties.getMchId()));
            payConfig.setMchKey(StrUtil.trimToNull(properties.getMchKey()));
            payConfig.setSubAppId(StrUtil.trimToNull(properties.getSubAppId()));
            payConfig.setSubMchId(StrUtil.trimToNull(properties.getSubMchId()));
            payConfig.setKeyPath(StrUtil.trimToNull(properties.getKeyPath()));

            // 可以指定是否使用沙箱环境
            payConfig.setUseSandboxEnv(false);

            wxPayService.setConfig(payConfig);
        }
        return wxPayService;
    }

}
