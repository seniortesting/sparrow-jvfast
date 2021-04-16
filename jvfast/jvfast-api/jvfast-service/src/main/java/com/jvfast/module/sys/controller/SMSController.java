package com.jvfast.module.sys.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jvfast.common.annotation.Limit;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.constant.NotificationConst;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.enums.SmsTemplateEnum;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.param.SMSCodeParam;
import com.jvfast.module.sys.service.SysUserService;
import com.jvfast.notification.sms.entity.SmsVerifyCodeRequest;
import com.jvfast.notification.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@Api(tags = "SMS接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/sms")
public class SMSController {

    private final SmsService smsService;
    private final SysUserService sysUserService;

    @Limit(name = "发送短信验证码", count = 1, seconds = 60)
    @Log(title = "发送短信验证码", businessType = BusinessTypeEnum.OTHER)
    @PostMapping("code")
    @ApiOperation(value = "发送短信验证码", notes = "发送阿里云的短信验证码服务")
    public Result sendSMSCode(@RequestBody @Valid SMSCodeParam smsCodeParam) {
        SmsTemplateEnum type = smsCodeParam.getType();
        String phone = smsCodeParam.getPhone();
        // 如果是找回密码和登录操作，首先验证是否存在该手机号码
        String templateCode = null;
        if (type.equals(SmsTemplateEnum.FORGET_PASSWD) || type.equals(SmsTemplateEnum.LOGIN) || type.equals(SmsTemplateEnum.BINDING_ACCOUNT)) {
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                    .select(SysUser::getId)
                    .eq(SysUser::getPhone, phone);
            SysUser sysUser = sysUserService.getOne(queryWrapper, false);
            if (sysUser == null) {
                return Result.fail(ResultCode.NOT_FOUND, "该手机号没有注册");
            }
            // 设置模板代码
            if (type.equals(SmsTemplateEnum.LOGIN)) {
                templateCode = NotificationConst.SMS_TEMPLATE_CODE_LOGIN;
            } else if (type.equals(SmsTemplateEnum.FORGET_PASSWD)) {
                templateCode = NotificationConst.SMS_TEMPLATE_CODE_FORGET_PASSWORD;
            } else if (type.equals(SmsTemplateEnum.BINDING_ACCOUNT)) {
                templateCode = NotificationConst.SMS_TEMPLATE_CODE_BINDING_ACCOUNT;
            }
        } else if (type.equals(SmsTemplateEnum.REGISTER)) {
            templateCode = NotificationConst.SMS_TEMPLATE_CODE_REGISTER;
        } else if (type.equals(SmsTemplateEnum.FREE_USER)) {
            templateCode = NotificationConst.SMS_TEMPLATE_CODE_FREE_USE;
        }
        if (!StrUtil.isEmpty(templateCode)) {
            String verifyCode = RandomUtil.randomNumbers(NotificationConst.VERIFY_CODE_LENGTH);
            SmsVerifyCodeRequest smsVerifyCodeRequest = new SmsVerifyCodeRequest();
            smsVerifyCodeRequest.setPhones(Lists.newArrayList(phone));
            smsVerifyCodeRequest.setTemplateCode(templateCode);
            smsVerifyCodeRequest.setVerifyCode(verifyCode);

            boolean sendSuccess = smsService.sendVerifyCode(smsVerifyCodeRequest);
            if (sendSuccess) {
                HashMap<Object, Object> hashMap = Maps.newHashMapWithExpectedSize(2);
                hashMap.put("phone", phone);
                return Result.success(hashMap);
            } else {
                return Result.fail(ResultCode.NOT_FOUND);
            }
        } else {
            return Result.fail(ResultCode.BAD_REQUEST);
        }
    }
}
