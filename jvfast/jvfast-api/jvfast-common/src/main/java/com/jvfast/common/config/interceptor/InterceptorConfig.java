package com.jvfast.common.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-08 17:26
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 静态资源会忽略,但是swagger的接口: /swagger-resources
        final String DEFAULT_IGNORE_PATTERN = "/swagger*/**";
//        registry.addInterceptor(requestTimeInterceptor())
//                .excludePathPatterns(DEFAULT_IGNORE_PATTERN)
//                .order(2);
    }
}
