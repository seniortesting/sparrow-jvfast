package com.jvfast.module.ignore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * maomiav.com 查询结果对象
 * </p>
 * @author Walter Hu
 * @date 2020-03-14
 */
@Data
@ApiModel(value = "PornMaomiQueryVo对象", description = "查询maomiav.com返回结果对象")
public class PornMaomiQueryVo implements Serializable {
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
    private LocalDateTime updateTime;
}
