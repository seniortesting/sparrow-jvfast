package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysFeedback;
import com.jvfast.module.sys.model.param.SysFeedbackAddParam;
import com.jvfast.module.sys.model.param.SysFeedbackQueryPageParam;
import com.jvfast.module.sys.model.param.SysFeedbackUpdateParam;
import com.jvfast.module.sys.model.vo.SysFeedbackQueryVo;
import com.jvfast.module.sys.service.SysFeedbackService;
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
 * 建议反馈表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-09
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysFeedback接口")
@RestController
@RequestMapping("/sys/feedback")
public class SysFeedbackController {

    private final SysFeedbackService sysFeedbackService;

    /**
     * 添加建议反馈表
     */
    @Log(title = "添加SysFeedback", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysFeedback对象", notes = "添加建议反馈表", response = Result.class)
    public Result<Boolean> addSysFeedback(@Valid @RequestBody SysFeedbackAddParam sysFeedbackAddParam) {
        boolean flag = sysFeedbackService.saveSysFeedback(sysFeedbackAddParam);
        return Result.status(flag);
    }

    /**
     * 修改建议反馈表
     */
    @Log(title = "修改SysFeedback", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysFeedback对象", notes = "修改建议反馈表", response = Result.class)
    public Result<Boolean> updateSysFeedback(@Valid @RequestBody SysFeedbackUpdateParam sysFeedbackUpdateParam) {
        boolean flag = sysFeedbackService.updateSysFeedbackById(sysFeedbackUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除建议反馈表
     */
    @Log(title = "删除SysFeedback", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysFeedback对象", notes = "删除建议反馈表", response = Result.class)
    public Result<Boolean> deleteSysFeedback(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysFeedbackService.removeSysFeedbackById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除建议反馈表
     */
    @Log(title = "批量删除SysFeedback", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysFeedback对象", notes = "批量删除建议反馈表", response = Result.class)
    public Result<Boolean> deleteSysFeedbacks(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = sysFeedbackService.removeSysFeedbackByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取建议反馈表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysFeedbackVo对象详情", notes = "建议反馈表", response = SysFeedback.class)
    public Result<SysFeedback> getSysFeedback(@Valid @RequestBody IdParam idParam) {
        SysFeedback sysFeedback = sysFeedbackService.getSysFeedbackById(idParam);
        return Result.success(sysFeedback);
    }

    /**
     * SysFeedback查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysFeedback列表", notes = "SysFeedback列表", response = SysFeedback.class)
    public Result<List<SysFeedbackQueryVo>> listSysFeedback(@Valid @RequestBody IdParam idParam) {
        List<SysFeedbackQueryVo> sysFeedbackList = sysFeedbackService.listSysFeedbackQueryVo(idParam);
        return Result.success(sysFeedbackList);
    }

    /**
     * 建议反馈表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysFeedbackQueryVo分页列表", notes = "建议反馈表分页列表", response = SysFeedbackQueryVo.class)
    public Result<IPage<SysFeedbackQueryVo>> pageSysFeedback(@Valid @RequestBody(required = false) SysFeedbackQueryPageParam sysFeedbackQueryPageParam) {
        IPage<SysFeedbackQueryVo> paging = sysFeedbackService.pageSysFeedbackQueryVo(sysFeedbackQueryPageParam);
        return Result.success(paging);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
