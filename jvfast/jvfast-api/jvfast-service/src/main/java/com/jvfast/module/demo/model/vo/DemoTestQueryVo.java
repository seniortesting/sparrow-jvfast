package com.jvfast.module.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 测试表(无用)查询结果对象
 * </p>
 * @author Walter Hu
 * @date 2020-06-18
 */
@Data
@ApiModel(value = "DemoTestQueryVo对象", description = "查询测试表(无用)返回结果对象")
public class DemoTestQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录唯一标识id
    */
    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;
    /**
     * 测试标题
    */
    @ApiModelProperty(value = "测试标题")
    private String title;
    /**
     * 测试内容
    */
    @ApiModelProperty(value = "测试内容")
    private String content;
    /**
     * 对应该条记录是否可用，0可用，1不可用
    */
    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;
    /**
     * 备注
    */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 对应记录的修订版本号,乐观锁
    */
    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    private Integer version;
    /**
     * 对应记录的创建时间
    */
    @ApiModelProperty(value = "对应记录的创建时间")
    private LocalDateTime createTime;
    /**
     * 对应创建记录的人
    */
    @ApiModelProperty(value = "对应创建记录的人")
    private String createBy;
    /**
     * 对应记录的最后修改时间
    */
    @ApiModelProperty(value = "对应记录的最后修改时间")
    private LocalDateTime updateTime;
    /**
     * 对应最后修改记录的人
    */
    @ApiModelProperty(value = "对应最后修改记录的人")
    private String updateBy;

}
