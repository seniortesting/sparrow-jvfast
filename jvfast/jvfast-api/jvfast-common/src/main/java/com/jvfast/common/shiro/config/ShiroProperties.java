package com.jvfast.common.shiro.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "jvfast.shiro")
public class ShiroProperties {

    private boolean enable;
    private JWT jwt;
    private String[] filterChainDefinitions;
    @NestedConfigurationProperty
    private List<ShiroPermissionProperties> permission;

    @Data
    public static class JWT {
        // 签发人
        private String issuer;
        //主题
        private String subject;
        // 签发的目标
        private String audience;


        private String tokenHeader = "X-Token";
        private String secretKey;
        // 10天过期的jwt
        private Integer expiredHours = 240;
        private Integer refreshCountDownHours = 24;
    }

    @Data
    public static class ShiroPermissionProperties {

        /**
         * 单路径
         */
        private String url;
        /**
         * 路径数组
         */
        private String[] urls;

        /**
         * 权限
         */
        private String permission;

    }

}
