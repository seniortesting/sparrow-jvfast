package com.jvfast.wx.miniapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jvfast.wx.miniapp.config.WxMaProperties;
import com.jvfast.wx.miniapp.model.param.MaConfigQueryPageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/ma/cfg")
public class WxMaConfigController {

    private final WxMaProperties wxMaProperties;

    /**
     * 获取已经配置的小程序列表
     * @param mpConfigQueryPageParam
     * @return
     */
    @PostMapping("/page")
    public IPage<WxMaProperties.Config> getMaList(MaConfigQueryPageParam mpConfigQueryPageParam) {
        Page<WxMaProperties.Config> configPage = new Page<>();
        configPage.setCurrent(mpConfigQueryPageParam.getPageNo());
        configPage.setSize(mpConfigQueryPageParam.getPageSize());
        List<WxMaProperties.Config> configs = wxMaProperties.getConfigs();
        // 获取小程序列表
        configPage.setRecords(configs);
        return configPage;
    }
}
