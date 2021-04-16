package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class QRCodeParam {

    @ApiModelProperty("二维码的内容,可以是地址或者文本内容")
    @NotEmpty
    private String content;
}
