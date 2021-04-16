package com.jvfast.common.config.filter;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author walter
 * 或者直接在controller加上： @CrossOrigin
 * 这个类在启动的时候是不会执行的，而是在每个controller的方法执行的时候执行到这里
 * 这里不再配置，由nginx来进行配置
 */
public class CrossDomainFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,Content-Length,Accept,Origin,User-Agent,Cache-Control,X-Requested-With,X-Token");
        // https://stackoverflow.com/questions/37897523/axios-get-access-to-response-header-fields,返回的哪些header会报了到客户端,例如axios的response
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range, X-Token");


        HttpServletRequest httpServletRequest = request;
        String method = httpServletRequest.getMethod();
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(method)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        filterChain.doFilter(request, httpServletResponse);
    }
}
