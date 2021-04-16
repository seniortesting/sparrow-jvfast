package com.jvfast.common.config.filter;

import com.jvfast.common.config.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/**
 * 过滤器配置
 * @author Walter
 */
@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean corsFilter = createFilterRegistration(new CrossDomainFilter(), "corsFilter", Ordered.HIGHEST_PRECEDENCE, null);
        return corsFilter;
    }

    @Bean
    public FilterRegistrationBean contentEncodingRegistration() {
        FilterRegistrationBean corsFilter = createFilterRegistration(new ContentEncodingFilter(), "contentEncodingFilter", Ordered.HIGHEST_PRECEDENCE, null);
        return corsFilter;
    }

//    @Bean
//    public FilterRegistrationBean xssFilterRegistration() {
//        FilterRegistrationBean xssFilter = createFilterRegistration(new XssFilter(), "xssFilter", Ordered.LOWEST_PRECEDENCE, null);
//        return xssFilter;
//    }

    private FilterRegistrationBean createFilterRegistration(Filter filter, String name, int order, String urlPatterns) {
        String DEFAULT_URL_PATTERNS = "/*";
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setName(name);
        filterRegistrationBean.setOrder(order);
        filterRegistrationBean.addUrlPatterns(urlPatterns != null ? urlPatterns : DEFAULT_URL_PATTERNS);
        return filterRegistrationBean;
    }
}
