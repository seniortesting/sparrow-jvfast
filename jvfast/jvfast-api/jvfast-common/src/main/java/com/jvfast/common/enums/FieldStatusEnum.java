package com.jvfast.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FieldStatusEnum {
    /**
     * 对应搜索的状态
     */
    DISABLE(0, "禁用"),
    ENABLE(1, "启用"),
    ALL(2, "全部");

    //@JsonValue
    private int code;
    private String desc;
}
