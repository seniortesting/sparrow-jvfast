package com.jvfast.common.annotation;

import com.jvfast.common.shiro.config.ShiroConfig;

import java.lang.annotation.*;

/**
 * @author Walter
 * @deprecated {@link ShiroConfig}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IgnoreSecurity {
}
