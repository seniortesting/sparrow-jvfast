package com.jvfast.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Walter
 * 配置通用配置
 */
@Configuration
@EnableConfigurationProperties(JVFastCommonProperties.class)
public class JVFastCommonConfig {
}
