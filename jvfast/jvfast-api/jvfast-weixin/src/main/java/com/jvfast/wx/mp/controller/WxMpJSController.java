package com.jvfast.wx.mp.controller;

import cn.hutool.core.util.StrUtil;
import com.jvfast.common.api.Result;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.wx.mp.model.param.MpJSConfigParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/mp/js/{appid}")
public class WxMpJSController {
    private final WxMpService wxMpService;


    @PostMapping("/sign")
    public Result<WxJsapiSignature> sign(@PathVariable String appid, @Valid @RequestBody MpJSConfigParam mpJSConfigParam) {
        if (!wxMpService.switchover(appid)) {
            throw new BadRequestException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        // 1.拼接url（当前网页的URL，不包含#及其后面部分）
        // String wxUrl = request.getHeader("Referer");
        String wxUrl = mpJSConfigParam.getUrl();
        if (!StrUtil.isEmpty(wxUrl)) {
            wxUrl = wxUrl.split("#")[0];
        } else {
            return Result.fail("地址不正确，微信配置加载失败");
        }
        String errMsg;
        try {
            log.info("产生对应的js签名，请求地址是: {}", wxUrl);
            WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(wxUrl);
            log.info("产生对应的js签名, 返回签名信息是: {}", jsapiSignature);
            return Result.success(jsapiSignature);
        } catch (WxErrorException e) {
            errMsg = e.getError().getErrorMsg();
            log.error("微信jsapi配置出错", e);
        } catch (Exception e) {
            errMsg = e.getMessage();
            log.error("微信jsapi配置未捕获的异常", e);
        }
        return Result.fail(errMsg);
    }

}
