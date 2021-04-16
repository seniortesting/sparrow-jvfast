package com.jvfast.wx.mp.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MpJSConfigParam {
    @NotBlank(message = "请求url不能为空")
    private String url;
}
