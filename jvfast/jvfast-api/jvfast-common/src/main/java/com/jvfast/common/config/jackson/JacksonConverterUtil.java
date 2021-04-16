package com.jvfast.common.config.jackson;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 配置所有的类型转换器,例如date,Double,Integer
 *
 * @author Walter Hu
 * @date 2019/12/18
 * @since 1.8
 */
public class JacksonConverterUtil {

    // 日期序列化
    public static class JacksonDateSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String string = null;
            if (date != null) {
                string = DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
            }
            jsonGenerator.writeString(string);
        }
    }

    // 日志反序列化
    public static class JacksonDateDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            String date = jp.getText();
            return DateUtil.parseDate(date);
        }
    }

    // 整形数据反序列化
    public static class JacksonIntegerDeserializer extends JsonDeserializer<Integer> {

        @Override
        public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String string = jsonParser.getText();
            return NumberUtil.parseInt(string);
        }
    }

    // double数据反序列化
    public static class JacksonDoubleDeserializer extends JsonDeserializer<Double> {

        @Override
        public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String string = jsonParser.getText();
            if (StrUtil.isBlank(string)) {
                return null;
            }
            Double d = Double.parseDouble(string);
            return d;
        }
    }
}
