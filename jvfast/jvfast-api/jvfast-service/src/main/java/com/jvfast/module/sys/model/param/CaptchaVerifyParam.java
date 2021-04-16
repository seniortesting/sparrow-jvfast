package com.jvfast.module.sys.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-12 10:42
 **/
@Data
public class CaptchaVerifyParam {

    @Length(min = 5, max = 5, message = "验证码输入错误")
    private String code;
    @NotBlank(message = "输入参数非法")
    private String session;
}
