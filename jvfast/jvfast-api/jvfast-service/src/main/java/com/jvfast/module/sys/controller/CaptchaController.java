package com.jvfast.module.sys.controller;

import com.jvfast.common.annotation.Limit;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.config.captcha.Captcha;
import com.jvfast.module.sys.model.param.CaptchaVerifyParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Api(tags = "Captcha接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/captcha")
public class CaptchaController {

    private final Captcha captcha;


    @Limit(name = "发送验证码", count = 5, seconds = 60)
    @PostMapping("img")
    @ApiOperation(value = "产生5位字符验证码", notes = "点击或者刷新生成新的验证码")
    public Result render() {
        Map captchaData = captcha.render();
        return Result.success(captchaData);
    }

    @PostMapping("verify")
    @ApiOperation(value = "校验验证码", notes = "提交的验证码和session中对应的该验证码比对")
    public Result validDefaultTime(@RequestBody @Valid CaptchaVerifyParam captchaVerifyParam) {
        //default timeout 900 seconds
        String code = captchaVerifyParam.getCode();
        String session = captchaVerifyParam.getSession();
        String captchaValue = captcha.getCaptcha(session);
        if (null == captchaValue) {
            return Result.fail(ResultCode.NOT_FOUND, "没有找到对应验证码");
        }
        if (!code.equalsIgnoreCase(captchaValue)) {
            return Result.fail(ResultCode.BAD_REQUEST, "错误验证码");
        }
        return Result.success("校验成功");

    }
}
