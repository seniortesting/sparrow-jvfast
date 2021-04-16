package com.jvfast.wx.mp.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class WxMpPage<T> extends Page<T> {
    private String nextOpenid;
}
