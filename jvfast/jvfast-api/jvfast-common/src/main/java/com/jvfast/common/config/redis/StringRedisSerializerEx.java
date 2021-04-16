package com.jvfast.common.config.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 重写
 *
 * @author Walter
 */
public class StringRedisSerializerEx implements RedisSerializer<Object> {

    private final Charset charset;

    public StringRedisSerializerEx() {
        this(StandardCharsets.UTF_8);
    }

    public StringRedisSerializerEx(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }


    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        String string = JSONUtil.toJsonStr(o);
        if (StrUtil.isBlank(string)) {
            return null;
        }
        string = string.replace("\"", "");
        return string.getBytes(charset);
    }

}
