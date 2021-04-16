package com.jvfast.common.config.security;

import cn.hutool.core.io.IoUtil;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 全局发送的请求加密
 * 参考: https://www.jianshu.com/p/c7ea4d1a8b3b
 */
@Slf4j
@RequiredArgsConstructor
// 执行在convertMessage之前
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalDecryptRequestBodyAdvice implements RequestBodyAdvice {

    private final JVFastCommonProperties jvFastCommonProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 针对那种进行过滤
        boolean hasParameterAnnotation = methodParameter.hasParameterAnnotation(RequestBody.class);
        return hasParameterAnnotation;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        // 此处进行数据解密
        HttpInputMessage httpInputMessage = decryptBody(inputMessage);
        return httpInputMessage;
    }

    /**
     * 进行数据的解密操作
     *
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }


    /**
     * 解密消息体, aes解密,只针对json的数据格式进行解密,其他的数据不进行解密
     *
     * @param inputMessage
     * @return
     * @throws IOException
     */
    private HttpInputMessage decryptBody(HttpInputMessage inputMessage) throws IOException {
        JVFastCommonProperties.Encrypt encrypt = jvFastCommonProperties.getEncrypt();
        if (encrypt.getEnabled()) {
            String contentTypeSubType = inputMessage.getHeaders().getContentType().getSubtype();
            // 进行json的数据进行拦截解密,其他的数据不用解密
            String applicationJsonUtf8Subtype = MediaType.APPLICATION_JSON.getSubtype();
            if (contentTypeSubType.equals(applicationJsonUtf8Subtype)) {
                InputStream encryptStream = inputMessage.getBody();
                String encodedStr = IoUtil.read(encryptStream, StandardCharsets.UTF_8);
                // 直接进行url转码了,所以这里需要进行判断
                String applicationFormUrlencodedSubtype = MediaType.APPLICATION_FORM_URLENCODED.getSubtype();
                if (inputMessage.getHeaders().getContentType().getSubtype().equals(applicationFormUrlencodedSubtype)) {
                    encodedStr = URLDecoder.decode(encodedStr, StandardCharsets.UTF_8.name());
                }
                String decodedStr = EncryptUtil.AESDecode(encodedStr, encrypt.getSecretKey());
                if (decodedStr == null) {
                    throw new BadRequestException("解析加密的请求数据失败!");
                }
                ByteArrayInputStream byteArrayInputStream = IoUtil.toStream(decodedStr, StandardCharsets.UTF_8);
                // 构造新的结果
                HttpInputMessage httpInputMessage = new HttpInputMessage() {
                    @Override
                    public HttpHeaders getHeaders() {
                        return inputMessage.getHeaders(); // 请求的header
                    }

                    @Override
                    public InputStream getBody() {
                        return byteArrayInputStream;
                    }
                };
                return httpInputMessage;
            }
        }

        return inputMessage;
    }
}
