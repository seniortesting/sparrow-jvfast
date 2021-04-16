package com.jvfast.common.annotation;


import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.enums.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author Walter
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String title() default "";

    /**
     * 功能
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 操作人类别
     */
    OperatorTypeEnum operatorType() default OperatorTypeEnum.BACKEND;

}
