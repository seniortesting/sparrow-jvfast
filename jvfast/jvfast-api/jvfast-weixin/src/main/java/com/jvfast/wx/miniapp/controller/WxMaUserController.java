package com.jvfast.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.wx.common.converter.WxUserConverter;
import com.jvfast.wx.common.entity.WxUserDetail;
import com.jvfast.wx.common.event.WxUserInfoStoreEvent;
import com.jvfast.wx.miniapp.config.WxMaConfiguration;
import com.jvfast.wx.miniapp.model.param.MaSessionParam;
import com.jvfast.wx.miniapp.model.param.MaUserInfoParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@RequestMapping("/wx/ma/user/{appid}")
public class WxMaUserController {


    /**
     * 兼容低版本问题
     * @deprecated  @link login
     * @param appid
     * @param code
     * @return
     */
    @PostMapping("/session/{code}")
    public Result<WxMaJscode2SessionResult> login(@PathVariable String appid, @PathVariable("code") String code) {
        MaSessionParam maSessionParam = new MaSessionParam();
        maSessionParam.setCode(code);
        return login(appid, maSessionParam);
    }
    /**
     * 登陆接口
     */
    @PostMapping("/session")
    public Result<WxMaJscode2SessionResult> login(@PathVariable String appid, @Valid @RequestBody MaSessionParam maSessionParam) {
        String code = maSessionParam.getCode();
        String userId = maSessionParam.getUserId();
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("对应的小程序微信登录code参数不能为空");
        }
        final WxMaService wxMaService = WxMaConfiguration.getMaService(appid);
        try {
            log.info("换取微信小程序sesssionKey请求,传递参数code: {}", code);
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.getUserService().getSessionInfo(code);
            log.info("换取微信小程序sesssionKey返回信息是:{}", wxMaJscode2SessionResult.toString());
            if (wxMaJscode2SessionResult != null) {
                //TODO 可以增加自己的逻辑，关联业务相关数据
                //会话密钥 session_key 是对用户数据进行 加密签名 的密钥。
                // 为了应用自身的数据安全，开发者服务器不应该把会话密钥下发到小程序，也不应该对外提供这个密钥。
                String openid = wxMaJscode2SessionResult.getOpenid();
                String unionid = wxMaJscode2SessionResult.getUnionid();
                WxUserDetail wxUserDetail = new WxUserDetail();
                wxUserDetail.setAppId(appid);
                wxUserDetail.setUserId(userId);
                wxUserDetail.setOpenId(openid);
                wxUserDetail.setUnionId(unionid);
                log.info("换取sesssionKey返回后UserInfo信息后台存储请求: {}", wxUserDetail.toString());
                SpringUtil.publishEvent(new WxUserInfoStoreEvent(wxUserDetail));
                return Result.success(wxMaJscode2SessionResult);
            }
        } catch (WxErrorException e) {
            log.error("换取微信小程序sesssionKey返回异常结果", e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("换取微信小程序sesssionKey未捕获的异常", e);
            return Result.fail(e.getMessage());
        }
        return Result.fail(ResultCode.BUSSINESS_ERROR);
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @PostMapping("/info")
    public Result<WxMaUserInfo> info(@PathVariable String appid, @Valid @RequestBody MaUserInfoParam maUserInfoParam) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        String userId = maUserInfoParam.getUserId();
        String sessionKey = maUserInfoParam.getSessionKey();
        String encryptedData = maUserInfoParam.getEncryptedData();
        String signature = maUserInfoParam.getSignature();
        String rawData = maUserInfoParam.getRawData();
        String iv = maUserInfoParam.getIv();
        // TODO 此处的校验发现容易失败，是否需要删除此代码？？？
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            String checkError = "对应的小程序微信用户signature不匹配参数错误";
            log.error(checkError);
        }
        // 解密用户信息
        String currentSeconds = String.valueOf(DateUtil.currentSeconds());
        log.info("换取微信小程序UserInfo请求,传递参数: {}", maUserInfoParam.toString());
        WxMaUserInfo wxMaUserInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        log.info("换取微信小程序UserInfo请求,返回结果:{}", wxMaUserInfo.toString());
        WxUserDetail wxUserDetail = WxUserConverter.INSTANCE.fromWxMaUser(wxMaUserInfo);
        wxUserDetail.setAppId(appid);
        wxUserDetail.setUserId(userId);
        wxUserDetail.setSubscribe(true);
        wxUserDetail.setSubscribeTime(currentSeconds);
        log.info("UserInfo信息后台存储请求: {}", wxUserDetail.toString());
        SpringUtil.publishEvent(new WxUserInfoStoreEvent(wxUserDetail));
        return Result.success(wxMaUserInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JSONUtil.toJsonStr(phoneNoInfo);
    }

}
