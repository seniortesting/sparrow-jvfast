package com.jvfast.common.config.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class JacksonUtil {


    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static ObjectMapper INSTANCE = JacksonObjectMapper();
    }

    public static ObjectMapper JacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return build(objectMapper);
    }

    public static ObjectMapper build(ObjectMapper objectMapper) {
        //设置地点为中国
        objectMapper.setLocale(Locale.CHINA);
        //异常处理
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //反序列化时，属性不存在的兼容处理s
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 序列化处理
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时，日期的统一格式
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN, Locale.CHINA));

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        SimpleModule simpleModule = new SimpleModule();
        // Long类型序列化成字符串，避免Long精度丢失, 比如对于主键是Long类型的
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        /// XSS序列化
//        simpleModule.addSerializer(String.class, new XssJacksonSerializer());
//        simpleModule.addDeserializer(String.class, new XssJacksonDeserializer());
        // 基础日期时间序列化
        simpleModule.addSerializer(Date.class, new JacksonConverterUtil.JacksonDateSerializer());
        simpleModule.addDeserializer(Date.class, new JacksonConverterUtil.JacksonDateDeserializer());

        simpleModule.addDeserializer(Integer.class, new JacksonConverterUtil.JacksonIntegerDeserializer());
        simpleModule.addDeserializer(Double.class, new JacksonConverterUtil.JacksonDoubleDeserializer());
        // jdk8日期序列化和反序列化设置
        Java8TimeModule javaTimeModule = new Java8TimeModule();

        objectMapper.registerModule(simpleModule)
                .registerModule(javaTimeModule)
                .registerModule(new Jdk8Module());
        // 不要使用否则反序列化失败
        // 注意：使用 @JsonValue和@sonCreator的时候，直接在@JsonCreator中声明 @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        // DELEGATING 单参数, PROPERTIES: 多参数
        // .registerModule(new ParameterNamesModule(JsonCreator.Mode.DEFAULT));
        return objectMapper;
    }

    /**
     * 功能描述: 序列化, 将对象序列化成json字符串
     *
     * @Param: [object]
     * @return: java.lang.String
     * @Author: Walter Hu
     * @Date: 2019/1/5 0005
     */
    public static <T> String toStr(T object) {
        String valueAsString = null;
        try {
            valueAsString = getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueAsString;
    }


    /**
     * 序列化
     * <p>
     * 功能描述: 序列化，对象转换成字节数组
     *
     * @Param: [object]
     * @return: java.lang.String
     * @Author: Walter Hu
     * @Date: 2019/1/5 0005
     */
    public static byte[] toBytes(Object object) {
        byte[] valueAsBytes = null;
        try {
            valueAsBytes = getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueAsBytes;
    }


    /**
     * 反序列化
     * <p>
     * 功能描述:  将POJO/JSON字符串转为常用类型或者POJO类： Map.class， List.class或POJO.class
     * POJO => Map.class： {"name": "test"}
     * POJO => List.class： [{"name": "test"}[
     * String => Map.class: {"name": "test"}
     * String => List.class: [{"name":"test"}]
     * String => POJO.class: {}
     * <p>
     * 也可以是字节，文件，或者字节流
     * 例如: {"status":0,"result":{"location":{"lng":121.50640133351109,"lat":31.24510456140524},"formatted_address":"上海市浦东新区世纪大道1","business":"东方路,陆家嘴,外滩","addressComponent":{"country":"中国","country_code":0,"country_code_iso":"CHN","country_code_iso2":"CN","province":"上海市","city":"上海市","city_level":2,"district":"浦东新区","town":"","adcode":"310115","street":"世纪大道","street_number":"1","direction":"东","distance":"75"},"pois":[],"roads":[],"poiRegions":[],"sematic_description":"","cityCode":289}}
     *
     * @Param: [jsonStr, clazz]
     * @return: T
     * @Author: Walter Hu
     * @Date: 2019/1/5 0005
     */
    public static <T> T parse(Object content, Class<T> clazz) {
        T value = null;
        try {
            if (content instanceof Map) {
                value = getInstance().convertValue(content, clazz);
            } else if (content instanceof String) {
                String str = (String) content;
                value = getInstance().readValue(str, clazz);
            } else if (content instanceof byte[]) {
                byte[] bytes = (byte[]) content;
                value = getInstance().readValue(bytes, clazz);
            } else if (content instanceof InputStream) {
                InputStream inputStream = (InputStream) content;
                value = getInstance().readValue(inputStream, clazz);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}

