package com.jvfast.wx.mp.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.wx.mp.model.param.MpUserQueryPageParam;
import com.jvfast.wx.mp.model.vo.WxMpPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/mp/user/{appid}")
public class WxMpUserController {
    private final WxMpService wxMpService;
    private static final int MAX_OPENID_SIZE = 100;

    /**
     * 公众号对应的关注用户列表
     *
     * @param appid
     * @param mpUserQueryPageParam
     * @return
     */
    @PostMapping("/page")
    public WxMpPage<WxMpUser> userPageList(@PathVariable String appid, @RequestBody MpUserQueryPageParam mpUserQueryPageParam) {
        if (!wxMpService.switchover(appid)) {
            throw new BadRequestException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        // 用户列表
        WxMpPage<WxMpUser> page = new WxMpPage<>();
        Integer pageNo = mpUserQueryPageParam.getPageNo();
        Integer pageSize = mpUserQueryPageParam.getPageSize();
        String searchNextOpenid = mpUserQueryPageParam.getNextOpenid();
        searchNextOpenid = StrUtil.isNotEmpty(searchNextOpenid) ? searchNextOpenid : null;
        try {
            log.info("拉取微信公众号用户列表,参数是: {}", mpUserQueryPageParam.toString());
            WxMpUserService wxMpServiceUserService = wxMpService.getUserService();
            WxMpUserList wxMpUserList = wxMpServiceUserService.userList(searchNextOpenid);
            String nextOpenid = wxMpUserList.getNextOpenid();
            List<String> openids = wxMpUserList.getOpenids();
            if (!openids.isEmpty()) {
                // 因为上面的方法一次最多可以拉去10000条数据,此处最多可以拉去100条，需要截取
                List<String> listOpenIds = openids.size() > MAX_OPENID_SIZE ? CollectionUtil.sub(openids, 0, MAX_OPENID_SIZE) : openids;
                nextOpenid = openids.size() > MAX_OPENID_SIZE ? openids.get(MAX_OPENID_SIZE + 1) : nextOpenid;
                List<WxMpUser> wxMpUsers = wxMpServiceUserService.userInfoList(listOpenIds);
                page.setRecords(wxMpUsers);
            }
            // TODO 无法拉取所有的公众号用户的信息，按照微信接口最多可拉取1000个用户
            page.setTotal(openids.size());
            page.setNextOpenid(nextOpenid);
        } catch (WxErrorException e) {
            String errMsg = e.getError().getErrorMsg();
            log.error(errMsg);
        } catch (Exception e) {
            log.error("拉取公众号用户列表出错，错误信息: ",e);
        }
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }
}
