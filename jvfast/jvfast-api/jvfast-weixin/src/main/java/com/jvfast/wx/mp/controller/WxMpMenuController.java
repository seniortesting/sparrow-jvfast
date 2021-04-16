package com.jvfast.wx.mp.controller;

import com.jvfast.common.api.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/mp/menu/{appid}")
public class WxMpMenuController {

    private final WxMpService wxMpService;


    /**
     * <pre>
     * 自定义菜单查询接口
     * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN
     * </pre>
     */
    @PostMapping("/list")
    public Result<WxMpMenu> menuGet(@PathVariable String appid) throws WxErrorException {
        try {
            WxMpMenu wxMpMenu = wxMpService.switchoverTo(appid).getMenuService().menuGet();
            return Result.success(wxMpMenu);
        } catch (Exception e) {
            String errMsg = e.getMessage();
            log.error("获取菜单出错", errMsg);
            return Result.fail(errMsg);
        }
    }

    /**
     * <pre>
     * 自定义菜单创建接口
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
     * 如果要创建个性化菜单，请设置matchrule属性
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
     * </pre>
     *
     * @return 如果是个性化菜单，则返回menuid，否则返回null
     */
    @PostMapping("/add")
    public Result<String> menuCreate(@PathVariable String appid, @RequestBody WxMenu menu) throws WxErrorException {
        try {
            return Result.success(wxMpService.switchoverTo(appid).getMenuService().menuCreate(menu));
        } catch (Exception e) {
            String errMsg = e.getMessage();
            log.error("创建菜单出错", errMsg);
            return Result.fail(errMsg);
        }
    }

    /**
     * <pre>
     * 自定义菜单创建接口
     * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
     * 如果要创建个性化菜单，请设置matchrule属性
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
     * </pre>
     *
     * @return 如果是个性化菜单，则返回menuid，否则返回null
     */
    @PostMapping("/addByJson")
    public Result menuCreate(@PathVariable String appid, @RequestBody String json) throws WxErrorException {
        return Result.success(wxMpService.switchoverTo(appid).getMenuService().menuCreate(json));
    }

    /**
     * <pre>
     * 自定义菜单删除接口
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141015&token=&lang=zh_CN
     * </pre>
     */
    @PostMapping("/delete")
    public Result<Boolean> menuDelete(@PathVariable String appid) throws WxErrorException {
        boolean success;
        try {
            wxMpService.switchoverTo(appid).getMenuService().menuDelete();
            success = true;
        } catch (Exception e) {
            String errMsg = e.getMessage();
            log.error("删除菜单出错", errMsg);
            success = false;
        }
        return Result.status(success);
    }

    /**
     * <pre>
     * 删除个性化菜单接口
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
     * </pre>
     *
     * @param menuId 个性化菜单的menuid
     */
    @PostMapping("/delete/{menuId}")
    public void menuDelete(@PathVariable String appid, @PathVariable String menuId) throws WxErrorException {
        wxMpService.switchoverTo(appid).getMenuService().menuDelete(menuId);
    }

    /**
     * <pre>
     * 测试个性化菜单匹配结果
     * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
     * </pre>
     *
     * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
     */
    @PostMapping("/menuTryMatch/{userid}")
    public WxMenu menuTryMatch(@PathVariable String appid, @PathVariable String userid) throws WxErrorException {
        return wxMpService.switchoverTo(appid).getMenuService().menuTryMatch(userid);
    }

    /**
     * <pre>
     * 获取自定义菜单配置接口
     * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
     * 请注意：
     * 1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自定义菜单配置，并通过接口再次给公众号设置好自动回复规则，以提升公众号运营者的业务体验。
     * 2、本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到接口，而自定义菜单查询接口则仅能查询到使用API设置的菜单配置。
     * 3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
     * 4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
     * 5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
     *  接口调用请求说明:
     * http请求方式: GET（请使用https协议）
     * https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
     * </pre>
     */
    @PostMapping("/getSelfMenuInfo")
    public WxMpGetSelfMenuInfoResult getSelfMenuInfo(@PathVariable String appid) throws WxErrorException {
        return wxMpService.switchoverTo(appid).getMenuService().getSelfMenuInfo();
    }
}
