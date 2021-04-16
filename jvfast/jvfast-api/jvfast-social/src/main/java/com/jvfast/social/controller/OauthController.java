package com.jvfast.social.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 第三方登录 Controller
 * </p>
 *
 * @package: com.xkcoding.oauth.controller
 * @description: 第三方登录 Controller
 * @author: Walter Hu
 * @date: Created in 2020-05-17 10:07
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Walter Hu
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OauthController {

    private final AuthRequestFactory factory;

    /**
     * 登录类型
     */
//    @GetMapping
//    public List<String> loginType() {
//        List<String> oauthList = factory.oauthList();
//        return oauthList;
//    }

    /**
     * 登录
     *
     * @param type     第三方登录类型
     * @param response response
     * @throws IOException
     */
    @RequestMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info("login url [{}]: {} ", type, authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * 登录成功后的回调
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:9999/oauth/github/callback
     *
     * @param type     第三方登录类型
     * @param callback 携带返回的信息
     * @return 登录成功后的信息
     */
    @RequestMapping("/callback/{type}")
    public AuthResponse login(@PathVariable String type, AuthCallback callback) {
        AuthRequest authRequest = factory.get(type);
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", StrUtil.toString(response));
        if (response.ok()) {
            // 保存对应的授权登录成功的用户信息到后台数据库
        }
        return response;
    }
}
