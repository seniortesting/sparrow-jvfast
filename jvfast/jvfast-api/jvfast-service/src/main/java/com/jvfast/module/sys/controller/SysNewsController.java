package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysNews;
import com.jvfast.module.sys.model.param.SysNewsAddParam;
import com.jvfast.module.sys.model.param.SysNewsQueryPageParam;
import com.jvfast.module.sys.model.param.SysNewsUpdateParam;
import com.jvfast.module.sys.model.vo.SysNewsQueryVo;
import com.jvfast.module.sys.service.SysNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 消息新闻表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysNews接口")
@RestController
@RequestMapping("/sys/news")
public class SysNewsController {
    private final SysNewsService sysNewsService;

    /**
     * 添加消息新闻表
     */
    @Log(title = "新增新闻", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysNews对象", notes = "添加消息新闻表", response = Result.class)
    public Result<Boolean> addSysNews(@Valid @RequestBody SysNewsAddParam sysNewsAddParam) {
        boolean flag = sysNewsService.saveSysNews(sysNewsAddParam);
        return Result.status(flag);
    }

    /**
     * 修改消息新闻表
     */
    @Log(title = "修改新闻", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysNews对象", notes = "修改消息新闻表", response = Result.class)
    public Result<Boolean> updateSysNews(@Valid @RequestBody SysNewsUpdateParam sysNewsUpdateParam) {
        boolean flag = sysNewsService.updateSysNewsById(sysNewsUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除消息新闻表
     */
    @Log(title = "删除新闻", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysNews对象", notes = "删除消息新闻表", response = Result.class)
    public Result<Boolean> deleteSysNews(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysNewsService.removeSysNewsById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除新闻表
     */
    @Log(title = "批量删除新闻", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysNews对象", notes = "批量删除新闻表", response = Result.class)
    public Result<Boolean> deleteSysNews(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysNewsService.removeSysNewssById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取消息新闻表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysNewsVo对象详情", notes = "消息新闻表", response = SysNews.class)
    public Result<SysNews> getSysNews(@Valid @RequestBody IdParam idParam) {
        SysNews sysNews = sysNewsService.getSysNewsById(idParam);
        return Result.success(sysNews);
    }

    /**
     * SysNews查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysNews列表", notes = "SysNews列表", response = SysNews.class)
    public Result<List<SysNewsQueryVo>> getSysNewsList() {
        List<SysNewsQueryVo> sysNewsList = sysNewsService.listSysNewsQueryVo();
        return Result.success(sysNewsList);
    }


    /**
     * 消息新闻表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysNewsQueryVo分页列表", notes = "消息新闻表分页列表", response = SysNewsQueryVo.class)
    public Result<IPage<SysNewsQueryVo>> getSysNewsPageList(@Valid @RequestBody(required = false) SysNewsQueryPageParam sysNewsQueryPageParam) {
        IPage<SysNewsQueryVo> paging = sysNewsService.pageSysNewsQueryVo(sysNewsQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
