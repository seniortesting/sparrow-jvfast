package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysNotice;
import com.jvfast.module.sys.model.param.SysNoticeAddParam;
import com.jvfast.module.sys.model.param.SysNoticeQueryPageParam;
import com.jvfast.module.sys.model.param.SysNoticeUpdateParam;
import com.jvfast.module.sys.model.vo.SysNoticeQueryVo;
import com.jvfast.module.sys.service.SysNoticeService;
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
 * 通知公告表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysNotice接口")
@RestController
@RequestMapping("/sys/notice")
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;


    /**
     * 添加通知公告表
     */
    @Log(title = "新增公告", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysNotice对象", notes = "添加通知公告表", response = Result.class)
    public Result<Boolean> addSysNotice(@Valid @RequestBody SysNoticeAddParam sysNoticeAddParam) {
        boolean flag = sysNoticeService.saveSysNotice(sysNoticeAddParam);
        return Result.status(flag);
    }

    /**
     * 修改通知公告表
     */
    @Log(title = "修改公告", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysNotice对象", notes = "修改通知公告表", response = Result.class)
    public Result<Boolean> updateSysNotice(@Valid @RequestBody SysNoticeUpdateParam sysNoticeUpdateParam) {
        boolean flag = sysNoticeService.updateSysNoticeById(sysNoticeUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除通知公告表
     */
    @Log(title = "删除公告", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysNotice对象", notes = "删除通知公告表", response = Result.class)
    public Result<Boolean> deleteSysNotice(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysNoticeService.removeSysNoticeById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除公告表
     */
    @Log(title = "批量删除公告", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysNotice对象", notes = "批量删除公告表", response = Result.class)
    public Result<Boolean> deleteSysNotices(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysNoticeService.removeSysNoticesById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取通知公告表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysNoticeVo对象详情", notes = "通知公告表", response = SysNotice.class)
    public Result<SysNotice> getSysNotice(@Valid @RequestBody IdParam idParam) {
        SysNotice sysNotice = sysNoticeService.getSysNoticeById(idParam);
        return Result.success(sysNotice);
    }

    /**
     * SysNotice查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysNotice列表", notes = "SysNotice列表", response = SysNotice.class)
    public Result<List<SysNoticeQueryVo>> getSysNoticeList() {
        List<SysNoticeQueryVo> sysNoticeList = sysNoticeService.listSysNoticeQueryVo();
        return Result.success(sysNoticeList);
    }


    /**
     * 通知公告表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysNoticeQueryVo分页列表", notes = "通知公告表分页列表", response = SysNoticeQueryVo.class)
    public Result<IPage<SysNoticeQueryVo>> getSysNoticePageList(@Valid @RequestBody(required = false) SysNoticeQueryPageParam sysNoticeQueryPageParam) {
        IPage<SysNoticeQueryVo> paging = sysNoticeService.pageSysNoticeQueryVo(sysNoticeQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
