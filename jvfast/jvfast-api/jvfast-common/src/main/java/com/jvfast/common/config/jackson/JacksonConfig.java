package com.jvfast.common.config.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-09 00:04
 **/
@Configuration
public class JacksonConfig {

    @Primary
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        //创建ObjectMapper
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper = JacksonUtil.build(objectMapper);
        return objectMapper;
    }
}
