package com.jvfast.common.constraint;

import com.google.common.collect.Lists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * spring自带会校验输入的值是否是对应的枚举值,如果不是直接报错
 *
 * @deprecated
 */
public class EnumValidator implements ConstraintValidator<EnumType, Enum<?>> {
    @Override
    public void initialize(EnumType constraintAnnotation) {
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        List<Enum<?>> values = Lists.newArrayList();
        Enum<?>[] enumConstants = value.getDeclaringClass().getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            values.add(enumConstant);
        }
        return values.contains(value);
    }
}
