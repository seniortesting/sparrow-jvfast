package com.jvfast.module.sys.controller;

import com.jvfast.common.api.Result;
import com.jvfast.module.sys.model.entity.SysCity;
import com.jvfast.module.sys.model.vo.SysCityQueryVo;
import com.jvfast.module.sys.service.SysCityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md 前端控制器
 *
 * @author Walter Hu
 * @since 2020-02-07
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysCity接口")
@RestController
@RequestMapping("/sys/city")
public class SysCityController {

    private final SysCityService sysCityService;


    /**
     * 获取国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md
     */
    @GetMapping("/area/{areaCode}")
    @ApiOperation(value = "根据id获取SysCityVo对象详情", notes = "国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md", response = SysCity.class)
    public Result<List<SysCityQueryVo>> getSysCity(@PathVariable("areaCode") String areaCode) {
        List<SysCityQueryVo> sysCity = sysCityService.listSysCityQueryVo(areaCode);
        return Result.success(sysCity);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
