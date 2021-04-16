package com.jvfast.wx.mp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jvfast.wx.mp.config.WxMpProperties;
import com.jvfast.wx.mp.model.param.MpConfigQueryPageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/mp/cfg")
public class WxMpConfigController {

    private final WxMpProperties wxMpProperties;

    /**
     *  获取已经配置的公众号列表
     * @param mpConfigQueryPageParam
     * @return
     */
    @PostMapping("/page")
    public IPage<WxMpProperties.MpConfig> getMpList(MpConfigQueryPageParam mpConfigQueryPageParam) {
        Page<WxMpProperties.MpConfig> configPage = new Page<>();
        configPage.setCurrent(mpConfigQueryPageParam.getPageNo());
        configPage.setSize(mpConfigQueryPageParam.getPageSize());
        List<WxMpProperties.MpConfig> configs = wxMpProperties.getConfigs();
        // 获取公众号二维码
        configPage.setRecords(configs);
        return configPage;
    }
}
