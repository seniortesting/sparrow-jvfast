package com.jvfast.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 通用树结构,对于序列的字段进行排序
 * 使用方法:
 * 树结构实体类继承该方法
 */
//@JsonPropertyOrder({"id", "pid", "name", "children"})
@Data
public class Tree {

    private Long id;
    private Long pid;
    // 否则子类节点为空时是null结果
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Tree> children = Lists.newArrayList();
}
