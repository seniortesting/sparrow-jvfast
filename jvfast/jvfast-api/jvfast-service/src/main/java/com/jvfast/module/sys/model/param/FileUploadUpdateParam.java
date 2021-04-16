package com.jvfast.module.sys.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FileUploadUpdateParam {
    @NotNull(message = "图片id不能为空")
    private Long id;
    @NotBlank(message = "图片描述不能为空")
    private String remark;
}
