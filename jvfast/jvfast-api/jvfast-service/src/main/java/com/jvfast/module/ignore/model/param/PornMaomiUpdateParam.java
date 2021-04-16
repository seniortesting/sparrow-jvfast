package com.jvfast.module.ignore.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@ApiModel(value = "PornMaomiUpdateParam对象", description = "修改maomiav.com请求参数")
public class PornMaomiUpdateParam implements Serializable {
   private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "记录唯一标识id")
   private Long id;

   private String pageId;

   private String img;

   private String title;

   private String category;

   private String url;

   private String pageUrl;

   private LocalDate pornDate;
   private Long likeNum;

   private Integer version;
}
