package com.jvfast.module.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jvfast.common.config.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 测试表(无用)
 * </p>
 * @author Walter Hu
 * @since 2020-06-18
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("demo_test")
@ApiModel(value = "DemoTest对象", description = "测试表(无用)")
public class DemoTest extends BaseEntity {
    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "记录唯一标识id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "测试标题")
    private String title;

    @ApiModelProperty(value = "测试内容")
    private String content;


}
