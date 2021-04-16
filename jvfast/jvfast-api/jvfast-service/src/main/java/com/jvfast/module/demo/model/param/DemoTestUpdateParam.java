package com.jvfast.module.demo.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 测试表(无用)修改更新参数对象类
 * </p>
 * @author Walter Hu
 * @since 2020-06-18
 */
@Data
@NoArgsConstructor
@ApiModel(value = "DemoTestUpdateParam对象", description = "修改测试表(无用)请求参数")
public class DemoTestUpdateParam implements Serializable {
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
    * 对应记录是否可用，1可用，0不可用
   */
   @ApiModelProperty(value = "对应记录是否可用，1可用，0不可用")
   private Boolean status;
   /**
    * 对应记录备注
   */
   @ApiModelProperty(value = "对应记录备注")
   private String remark;
   /**
    * 对应记录的修订版本号,乐观锁
   */
   @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
   private Integer version;
   /**
    * 对应记录的最后修改时间
   */
   @ApiModelProperty(value = "对应记录的最后修改时间")
   private LocalDateTime updateTime;
   /**
    * 对应记录的最后修改者
   */
   @ApiModelProperty(value = "对应记录的最后修改者")
   private String updateBy;
}
