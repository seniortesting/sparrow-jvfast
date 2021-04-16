package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysNotification;
import com.jvfast.module.sys.model.param.SysNotificationAddParam;
import com.jvfast.module.sys.model.param.SysNotificationQueryPageParam;
import com.jvfast.module.sys.model.param.SysNotificationUpdateParam;
import com.jvfast.module.sys.model.vo.SysNotificationQueryVo;
import com.jvfast.module.sys.service.SysNotificationService;
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
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息) 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-22
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysNotification接口")
@RestController
@RequestMapping("/sys/notification")
public class SysNotificationController {

    private final SysNotificationService sysNotificationService;

    /**
     * 添加系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
     */
    @Log(title = "添加SysNotification", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysNotification对象", notes = "添加系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)", response = Result.class)
    public Result<Boolean> addSysNotification(@Valid @RequestBody SysNotificationAddParam sysNotificationAddParam) {
        boolean flag = sysNotificationService.saveSysNotification(sysNotificationAddParam);
        return Result.status(flag);
    }

    /**
     * 修改系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
     */
    @Log(title = "修改SysNotification", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysNotification对象", notes = "修改系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)", response = Result.class)
    public Result<Boolean> updateSysNotification(@Valid @RequestBody SysNotificationUpdateParam sysNotificationUpdateParam) {
        boolean flag = sysNotificationService.updateSysNotificationById(sysNotificationUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
     */
    @Log(title = "删除SysNotification", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysNotification对象", notes = "删除系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)", response = Result.class)
    public Result<Boolean> deleteSysNotification(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysNotificationService.removeSysNotificationById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
     */
    @Log(title = "批量删除SysNotification", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysNotification对象", notes = "批量删除系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)", response = Result.class)
    public Result<Boolean> deleteSysNotifications(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = sysNotificationService.removeSysNotificationByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysNotificationVo对象详情", notes = "系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)", response = SysNotification.class)
    public Result<SysNotification> getSysNotification(@Valid @RequestBody IdParam idParam) {
        SysNotification sysNotification = sysNotificationService.getSysNotificationById(idParam);
        return Result.success(sysNotification);
    }

    /**
     * SysNotification查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysNotification列表", notes = "SysNotification列表", response = SysNotification.class)
    public Result<List<SysNotificationQueryVo>> listSysNotification(@Valid @RequestBody(required = false) SysNotificationQueryPageParam sysNotificationQueryPageParam) {
        List<SysNotificationQueryVo> sysNotificationList = sysNotificationService.listSysNotificationQueryVo(sysNotificationQueryPageParam);
        return Result.success(sysNotificationList);
    }

    /**
     * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysNotificationQueryVo分页列表", notes = "系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)分页列表", response = SysNotificationQueryVo.class)
    public Result<IPage<SysNotificationQueryVo>> pageSysNotification(@Valid @RequestBody(required = false) SysNotificationQueryPageParam sysNotificationQueryPageParam) {
        IPage<SysNotificationQueryVo> paging = sysNotificationService.pageSysNotificationQueryVo(sysNotificationQueryPageParam);
        return Result.success(paging);
    }
    /***************************** 以下为扩展接口 ******************************************************/
    /**
     * 批量删除系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
     */
    @Log(title = "批量已读SysNotification", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/read")
    @ApiOperation(value = "批量已读SysNotification对象", notes = "批量已读系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)", response = Result.class)
    public Result<Boolean> readNotification(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = sysNotificationService.readNotifications(IdBatchParam);
        return Result.status(flag);
    }
}
