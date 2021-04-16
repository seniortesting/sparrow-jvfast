package com.jvfast.wx.mp.controller;

import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.wx.common.converter.WxUserConverter;
import com.jvfast.wx.common.entity.WxUserDetail;
import com.jvfast.wx.common.event.WxUserInfoStoreEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/mp/redirect/{appid}")
public class WxMpRedirectController {
    private final WxMpService wxMpService;

    /**
     * 公众号:网页授权获取用户基本信息
     *
     * @param appid
     * @param code
     * @return
     */
    @PostMapping("/auth/{code}")
    public Result<WxUserDetail> authUser(@PathVariable String appid, @PathVariable String code) {
        if (!wxMpService.switchover(appid)) {
            throw new BadRequestException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        String errorMsg;
        try {
            log.info("微信公众号oauth认证传递的code参数是: {}", code);
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            log.info("微信公众号oauth认证,返回结果是: {}", wxMpUser.toString());
            //广播用户认证信息，对应需要接受该信息即可
            WxUserDetail wxUserDetail = WxUserConverter.INSTANCE.fromWxMpUser(wxMpUser);
            String appId = wxMpService.getWxMpConfigStorage().getAppId();
            wxUserDetail.setAppId(appId);
            log.info("微信公众号oauth认证,发送用户信息存储是: {}", wxUserDetail.toString());
            SpringUtil.publishEvent(new WxUserInfoStoreEvent(wxUserDetail));
            return Result.success(wxUserDetail);
        } catch (WxErrorException e) {
            errorMsg = e.getError().getErrorMsg();
            log.error("微信公众号oauth认证异常失败： ", e);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            log.error("微信公众号未捕获异常失败： ", e);
        }
        return Result.fail(ResultCode.BUSSINESS_ERROR, errorMsg);
    }
}
