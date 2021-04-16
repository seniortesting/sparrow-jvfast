package com.jvfast.module.sys.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SysFileUploadUrlParam {
    @NotBlank(message = "文件url参数不能为空")
    private String url;
}
