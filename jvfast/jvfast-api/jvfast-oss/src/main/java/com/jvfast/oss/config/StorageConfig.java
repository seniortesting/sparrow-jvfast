package com.jvfast.oss.config;

import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.oss.service.StorageService;
import com.jvfast.oss.service.impl.ALiYunServiceImpl;
import com.jvfast.oss.service.impl.NoOpServiceImpl;
import com.jvfast.oss.service.impl.QCloudServiceImpl;
import com.jvfast.oss.service.impl.QiNiuServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 自动配置存储方式
 * @author Walter
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(StorageProperties.class)
@Configuration
public class StorageConfig {

    private final JVFastCommonProperties jvFastCommonProperties;
    private final StorageProperties storageProperties;

    @Bean
    public StorageService storageService() {
        StorageType storageType = storageProperties.getType();
        if (storageType.equals(StorageType.ALIYUN)) {
            return new ALiYunServiceImpl(storageProperties);
        } else if (storageType.equals(StorageType.QINIU)) {
            return new QiNiuServiceImpl(storageProperties);
        } else if (storageType.equals(StorageType.QCLOUD)) {
            return new QCloudServiceImpl(storageProperties);
        }
        return new NoOpServiceImpl(jvFastCommonProperties);
    }
}
