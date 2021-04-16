package com.jvfast.module.ignore.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * maomiav.com
 *
 * @author Walter Hu
 * @since 2020-03-14
 */
@Data
@NoArgsConstructor
@ApiModel(value = "PornMaomiAddParam对象", description = "添加maomiav.com请求参数")
public class PornMaomiAddParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pageId;

    private String img;

    private String title;

    private String category;

    private String url;

    private String pageUrl;

    private LocalDate pornDate;
}
