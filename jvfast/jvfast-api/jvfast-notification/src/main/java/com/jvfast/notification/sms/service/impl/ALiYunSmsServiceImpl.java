package com.jvfast.notification.sms.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.collect.Maps;
import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.config.redis.RedisClient;
import com.jvfast.notification.sms.config.SmsProperties;
import com.jvfast.notification.sms.entity.SmsRequest;
import com.jvfast.notification.sms.entity.SmsResponse;
import com.jvfast.notification.sms.entity.SmsVerifyCodeRequest;
import com.jvfast.notification.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ALiYunSmsServiceImpl implements SmsService {
    private static final String VERIFY_CODE_SESSION_KEY = "sms:code:";
    private static final String SUCCESS_CODE = "OK";
    private SmsProperties smsProperties;
    private IAcsClient acsClient;
    private RedisClient redisClient;
    private AsyncTask asyncTask;

    public ALiYunSmsServiceImpl(SmsProperties smsProperties, RedisClient redisClient, AsyncTask asyncTask) {
        this.smsProperties = smsProperties;
        this.redisClient = redisClient;
        this.asyncTask = asyncTask;
    }

    /**
     * 发送验证码短信,此处我们设定验证码的参数必须是code
     *
     * @param smsVerifyCodeRequest
     * @return
     */
    @Override
    public boolean sendVerifyCode(SmsVerifyCodeRequest smsVerifyCodeRequest) {
        SmsRequest smsRequest = new SmsRequest();
        String templateCode = smsVerifyCodeRequest.getTemplateCode();
        List<String> phones = smsVerifyCodeRequest.getPhones();
        String verifyCode = smsVerifyCodeRequest.getVerifyCode();

        smsRequest.setPhones(phones);
        smsRequest.setTemplateCode(templateCode);
        // 此处我们设定验证码的参数必须是code
        Map<String, String> params = Maps.newHashMap();
        params.put("code", verifyCode);
        smsRequest.setParams(params);
        boolean sendSuccess = send(smsRequest);
        if (sendSuccess) {
            // 存放到缓存中，以便比对
            int expiredSeconds = this.smsProperties.getExpiredSeconds();
            Duration duration = Duration.ofSeconds(expiredSeconds);
            phones.forEach(phone -> {
                String cacheKey = VERIFY_CODE_SESSION_KEY + phone;
                this.redisClient.setValue(cacheKey, verifyCode, duration);
            });
        }
        return sendSuccess;
    }

    /**
     * 批量发送短信
     *
     * @param smsRequest
     * @return
     */
    @Override
    public boolean send(SmsRequest smsRequest) {
        String signName = this.smsProperties.getAliyun().getSignName();
        String templateCode = smsRequest.getTemplateCode();
        Map<String, String> params = smsRequest.getParams();
        List<String> phones = smsRequest.getPhones();
        List<String> signNames = phones.stream().map(p -> {
            String sign = signName;
            return sign;
        }).collect(Collectors.toList());
        List<Map<String, String>> paramsMap = phones.stream().map(p -> {
            Map<String, String> param = params;
            return param;
        }).collect(Collectors.toList());

        String PhoneNumberJson = JSONUtil.toJsonStr(phones);
        String TemplateParamJson = JSONUtil.toJsonStr(paramsMap);

        // 发送短信
        Boolean resultStatus = null;
        String errorMsg = "";
        final CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");
        request.putQueryParameter("PhoneNumberJson", PhoneNumberJson);
        request.putQueryParameter("SignNameJson", JSONUtil.toJsonStr(signNames));
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParamJson", TemplateParamJson);
        try {
            final CommonResponse response = this.acsClient.getCommonResponse(request);
            String responseData = response.getData();
            SmsResponse smsResponse = JSONUtil.toBean(responseData, SmsResponse.class);
            String code = smsResponse.getCode();
            // 发送短信通知
            resultStatus = true;
            return SUCCESS_CODE.equalsIgnoreCase(code);
        } catch (final ClientException e) {
            errorMsg = e.getErrMsg();
            resultStatus = false;
            log.error("阿里云发送短信异常,请求数据是: {}", smsRequest, e);
        } finally {
            Boolean finalResultStatus = resultStatus;
            String finalErrorMsg = errorMsg;
            this.asyncTask.executeTask(() -> {
                phones.forEach(phone -> {
                    publishSmsNotification(phone, templateCode, TemplateParamJson, finalResultStatus, finalErrorMsg);
                });
            });
        }
        return false;
    }

    @Override
    public boolean checkVerifyCode(String phone, String code) {
        String cacheKey = VERIFY_CODE_SESSION_KEY + phone;
        Object cacheValue = this.redisClient.getValue(cacheKey);
        if (cacheValue != null) {
            return cacheValue.toString().equals(code);
        }
        return false;
    }

    @PostConstruct
    private void init() {
        SmsProperties.ALiYun aliyun = this.smsProperties.getAliyun();
        if (!Objects.isNull(aliyun)) {
            final IClientProfile clientProfile = DefaultProfile.getProfile(
                    "default", aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
            acsClient = new DefaultAcsClient(clientProfile);
        }
    }

}
