package com.jvfast.common.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-08 16:57
 **/
@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MessageConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(x -> x instanceof StringHttpMessageConverter || x instanceof AbstractJackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new ResourceRegionHttpMessageConverter());
        CustomMappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new CustomMappingJackson2HttpMessageConverter(objectMapper);
        // 可以设置支持更多数据类型
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(
                /// 备用
                // 支持的前台数据格式反序列化:
                // content-type: application/x-www-form-urlencoded, application/json, multipart/form-data, application/octet-stream
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON_UTF8,
                MediaType.MULTIPART_FORM_DATA,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_OCTET_STREAM
        ));
        mappingJackson2HttpMessageConverter.setPrettyPrint(false);
        converters.add(0, mappingJackson2HttpMessageConverter);

    }

    /**
     * see:  org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration#restTemplateBuilder()
     *
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        //放到第一个,使得以前的配置不生效
        configureMessageConverters(converters);
        return restTemplate;
    }
}
