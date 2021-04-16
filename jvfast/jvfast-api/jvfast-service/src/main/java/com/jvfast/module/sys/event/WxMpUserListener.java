package com.jvfast.module.sys.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jvfast.module.sys.converter.SysUserWxConverter;
import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.service.SysUserWxService;
import com.jvfast.wx.common.entity.WxUserDetail;
import com.jvfast.wx.common.event.WxUserInfoStoreEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class WxMpUserListener {

    private final SysUserWxService sysUserWxService;

    /**
     * 保存微信用户信息
     *
     * @param event
     */
    @Async
    @Order
    @EventListener(WxUserInfoStoreEvent.class)
    public void saveWxMpUser(WxUserInfoStoreEvent event) {
        WxUserDetail wxUserDetail = (WxUserDetail) event.getSource();
        String openId = wxUserDetail.getOpenId();
        String appId = wxUserDetail.getAppId();
        SysUserWx sysUserWx = SysUserWxConverter.INSTANCE.convertWxMpUser(wxUserDetail);
        sysUserWx.setUpdateBy(openId);
        sysUserWx.setCreateBy(openId);
        // 是否存在该记录
        LambdaQueryWrapper<SysUserWx> sysUserWxLambdaQueryWrapper = Wrappers.<SysUserWx>lambdaQuery()
                .select(SysUserWx::getAppId, SysUserWx::getOpenId,SysUserWx::getId)
                .eq(SysUserWx::getOpenId, openId)
                .eq(SysUserWx::getAppId, appId);
        SysUserWx foundSysUserWx = sysUserWxService.getOne(sysUserWxLambdaQueryWrapper, false);
        log.info("同步用户基本信息: {}到数据库", sysUserWx.toString());
        if (Objects.isNull(foundSysUserWx)) {
            // 没有找到该用户
            sysUserWxService.save(sysUserWx);
        } else {
            // 直接更新用户信息
            Long id = foundSysUserWx.getId();
            sysUserWx.setId(id);
            sysUserWxService.updateById(sysUserWx);
        }

    }
}
