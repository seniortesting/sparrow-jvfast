package com.jvfast.module.ignore.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jvfast.common.config.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>
 * maomiav.com
 * </p>
 * @author Walter Hu
 * @since 2020-03-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("porn_maomi")
@ApiModel(value = "PornMaomi对象", description = "maomiav.com")
public class PornMaomi extends BaseEntity {
    private static final long serialVersionUID=1L;
   /**
    * 记录唯一标识id
    */
   @ApiModelProperty(value = "记录唯一标识id")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;
   private String pageId;
   private String img;
   private String title;
   private String category;
   private String url;
   private String pageUrl;
   private LocalDate pornDate;
   private Long likeNum;


}
