package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息新闻表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysNewsAddParam对象", description = "添加消息新闻表请求参数")
public class SysNewsAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    private String fromSource;

    private String originalUrl;

    private LocalDateTime createTime;

}
