package com.jvfast.common.shiro.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.jvfast.common.shiro.exception.ShiroConfigException;
import com.jvfast.common.shiro.filter.JWTFilter;
import com.jvfast.common.shiro.matcher.JWTCredentialsMatcher;
import com.jvfast.common.shiro.realm.JWTRealm;
import com.jvfast.common.shiro.service.JWTRedisService;
import com.jvfast.common.shiro.service.JWTTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@EnableConfigurationProperties(ShiroProperties.class)
@Slf4j
@Configuration
public class ShiroConfig {


    /**
     * 参考对应用的shiro-spring-boot-starter中的配置
     *
     * @return
     * @see ShiroAutoConfiguration 这里主要实现的这个配置中的bean
     * 默认的该自动配置配置了如下:
     * 1. 对应的realm的认证策略:authenticationStrategy() 至少有一个认证通过就代表认证通过,修改为:  @see FirstSuccessfulStrategy
         * @see ShiroBeanAutoConfiguration  配置lifecycleBeanPostProcessor bean
         * @see ShiroAnnotationProcessorAutoConfiguration 配置Annotation的支持
         */
        private static final String SHIRO_FILTER_NAME = "shiroFilter";
        private static final String JWT_FILTER_NAME = "jwtFilter";


        @Bean
        public AuthenticationStrategy authenticationStrategy() {
            FirstSuccessfulStrategy firstSuccessfulStrategy = new FirstSuccessfulStrategy();
            return firstSuccessfulStrategy;
        }

        @Bean
        public Authenticator authenticator(List<Realm> realms) {
            ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
            authenticator.setRealms(realms);
            authenticator.setAuthenticationStrategy(authenticationStrategy());
            return authenticator;
        }

        @Bean
        public SessionManager sessionManager() {
            DefaultSessionManager sessionManager = new DefaultSessionManager();
            sessionManager.setSessionValidationSchedulerEnabled(false);
            return sessionManager;
        }

        @Bean
        public SessionStorageEvaluator sessionStorageEvaluator() {
            DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
            sessionStorageEvaluator.setSessionStorageEnabled(false);
            return sessionStorageEvaluator;
        }

    @Bean
    public SubjectDAO subjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return subjectDAO;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(List<Realm> realms) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRememberMeManager(null);
        securityManager.setRealms(realms);
        securityManager.setSubjectDAO(subjectDAO());
        securityManager.setSessionManager(sessionManager());

        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }


    @Bean
    public CredentialsMatcher credentialsMatcher(JWTTokenService JWTTokenService) {
        return new JWTCredentialsMatcher(JWTTokenService);
    }

    /**
     * @return
     */
    @Bean
    public Realm jwtRealm(JWTTokenService JWTTokenService, JWTRedisService loginRedisService) {
        JWTRealm jwtRealm = new JWTRealm(loginRedisService);
        jwtRealm.setCredentialsMatcher(credentialsMatcher(JWTTokenService));
        jwtRealm.setCachingEnabled(false);
        return jwtRealm;
    }

    @Bean(SHIRO_FILTER_NAME)
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,
                                              JWTTokenService JWTTokenService,
                                              JWTRedisService loginRedisService,
                                              ShiroProperties shiroProperties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自定义的过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(JWT_FILTER_NAME, createJWTFilter(JWTTokenService, loginRedisService));
        shiroFilterFactoryBean.setFilters(filterMap);
        // 路径配置
        Map<String, String> filterChainMap = shiroFilterChainDefinition(shiroProperties).getFilterChainMap();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(ShiroProperties shiroProperties) {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 全局通过路径
        Boolean enabledJwt = shiroProperties.isEnable();
        if (enabledJwt) {
            String[] definitions = shiroProperties.getFilterChainDefinitions();
            if (ArrayUtil.isNotEmpty(definitions)) {
                for (String definition : definitions) {
                    chainDefinition.addPathDefinition(definition, "anon");
                }
            }
            // 额外排除的地址
            //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常(可选,上面已经配置了对应的登录页面)
            List<ShiroProperties.ShiroPermissionProperties> permissionConfigs = shiroProperties.getPermission();
            if (CollectionUtil.isNotEmpty(permissionConfigs)) {
                for (ShiroProperties.ShiroPermissionProperties permissionConfig : permissionConfigs) {
                    String url = permissionConfig.getUrl();
                    String[] urls = permissionConfig.getUrls();
                    String permission = permissionConfig.getPermission();
                    if (StrUtil.isBlank(url) && ArrayUtil.isEmpty(urls)) {
                        throw new ShiroConfigException("shiro permission config 路径配置不能为空");
                    }
                    if (StrUtil.isBlank(permission)) {
                        throw new ShiroConfigException("shiro permission config permission不能为空");
                    }
                    if (StrUtil.isNotBlank(url)) {
                        chainDefinition.addPathDefinition(url, permission);
                    }
                    if (ArrayUtil.isNotEmpty(urls)) {
                        for (String urlStr : urls) {
                            chainDefinition.addPathDefinition(urlStr, permission);
                        }
                    }
                }
            }
            chainDefinition.addPathDefinition("/**", JWT_FILTER_NAME);
        } else {
            chainDefinition.addPathDefinition("/**", "anon");
        }
        return chainDefinition;
    }

    /**
     * 注册shiro的Filter，拦截请求
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager) throws Exception {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<Filter>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER_NAME);
        filterFilterRegistrationBean.setFilter(proxy);

        filterFilterRegistrationBean.setAsyncSupported(true);
        filterFilterRegistrationBean.setEnabled(true);
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);

        return filterFilterRegistrationBean;
    }

    /**
     * Enabling Shiro Annotations
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 否则方法上加 @RequiresPermissions报404错误
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    //注意不要加@Bean注解，不然spring会自动注册成filter
    protected JWTFilter createJWTFilter(JWTTokenService JWTTokenService, JWTRedisService loginRedisService) {
        JWTFilter jwtFilter = new JWTFilter(JWTTokenService, loginRedisService);
        return jwtFilter;
    }
}
