package com.jvfast.module.sys.event;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.service.SysUserWxService;
import com.jvfast.wx.common.event.WxMpUnsubscribleUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WxMpUserUnsubscribleListener {

    private final SysUserWxService sysUserWxService;

    /**
     * 取消关注微信用户信息
     *
     * @param event
     */
    @Async
    @Order
    @EventListener(WxMpUnsubscribleUserEvent.class)
    public void removeUser(WxMpUnsubscribleUserEvent event) {
        String openId = (String) event.getSource();
        LambdaUpdateWrapper<SysUserWx> sysUserWxLambdaQueryWrapper = Wrappers.<SysUserWx>lambdaUpdate()
                .set(SysUserWx::getSubscribe, false)
                .eq(SysUserWx::getOpenId, openId);
        log.info("取消关注公众号同步用户基本信息,openId是： {}",openId);
        sysUserWxService.update(sysUserWxLambdaQueryWrapper);
    }
}
