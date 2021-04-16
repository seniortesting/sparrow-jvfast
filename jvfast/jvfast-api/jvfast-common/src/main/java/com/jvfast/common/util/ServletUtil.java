/**
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jvfast.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * 获取当前请求的HttpServletRequest对象
 *
 * @author geekidea
 * @date 2018-11-08
 */
@Slf4j
public class ServletUtil {


    /**
     * 打印内容
     *
     * @param response
     * @param object
     * @throws Exception
     */
    public static void printJSONStr(HttpServletResponse response, Object object) {
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {

            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSONUtil.toJsonStr(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信配置，返回新型
     *
     * @param response HttpServletResponse
     * @param text     文本
     */
    public static void renderText(HttpServletResponse response, String text) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset:utf-8;");
        try (PrintWriter out = response.getWriter()) {
            out.append(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        return requestAttributes.getResponse();
    }

    /**
     * 获取getHeader
     */
    public static String getHeader(String header) {
        HttpServletRequest request = getRequest();
        if (null == request) {
            return null;
        }
        return request.getHeader(header);
    }

    /***
     * 获取 request 中 json 字符串的内容
     *
     * @param request request
     * @return 字符串内容
     */
    public static String getRequestParamString(HttpServletRequest request) {
        try {
            return getRequestStr(request);
        } catch (Exception ex) {
            return StrUtil.EMPTY;
        }
    }

    /**
     * 获取 request 请求内容
     *
     * @param request request
     * @return String
     * @throws IOException IOException
     */
    public static String getRequestStr(HttpServletRequest request) throws IOException {
        String queryString = request.getQueryString();
        if (StrUtil.isNotBlank(queryString)) {
            return new String(queryString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).replaceAll("&amp;", "&").replaceAll("%22", "\"");
        }
        return getRequestStr(request, getRequestBytes(request));
    }

    /**
     * 获取 request 请求的 byte[] 数组
     *
     * @param request request
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] getRequestBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 获取 request 请求内容
     *
     * @param request request
     * @param buffer  buffer
     * @return String
     * @throws IOException IOException
     */
    public static String getRequestStr(HttpServletRequest request, byte[] buffer) throws IOException {
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = StandardCharsets.UTF_8.name();
        }
        String str = new String(buffer, charEncoding).trim();
        if (StrUtil.isBlank(str)) {
            StringBuilder sb = new StringBuilder();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                String value = request.getParameter(key);
                sb.append(key + "=" + value + "&");
            }
            str = StrUtil.removeSuffix(sb.toString(), "&");
        }
        return str.replaceAll("&amp;", "&");
    }

    /**
     * 返回domain地址
     *
     * @return
     */
    public static String getDomain() {
        HttpServletRequest request = getRequest();
        String domain = getDomain(request);
        return domain;
    }

    private static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 返回domain地址,不带最后的反斜杠，例如返回结果： https://pingbook.top/test
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }


}
