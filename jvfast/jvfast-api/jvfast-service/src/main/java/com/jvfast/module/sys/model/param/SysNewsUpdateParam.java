package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
@ApiModel(value = "SysNewsUpdateParam对象", description = "修改消息新闻表请求参数")
public class SysNewsUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    @NotNull(message = "ID不能为空")
    private Long id;

    private String title;

    private String content;

    private String fromSource;

    private String originalUrl;

    private Boolean status;

    private LocalDateTime updateTime;

}
