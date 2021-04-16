package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysFileUpload;
import com.jvfast.module.sys.model.param.FileUploadParam;
import com.jvfast.module.sys.model.param.FileUploadUpdateParam;
import com.jvfast.module.sys.model.param.SysFileUploadUrlParam;
import com.jvfast.module.sys.model.vo.SysFileUploadQueryVo;
import com.jvfast.module.sys.service.SysFileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件上传表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysFileUpload接口")
@RestController
@RequestMapping("/sys/file")
public class SysFileUploadController {

    private final SysFileUploadService sysFileUploadService;

    /**
     * 单文件上传
     */
    @Log(title = "上传文件", businessType = BusinessTypeEnum.ADD)
    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传文件", notes = "添加文件上传数据表", response = Boolean.class)
    public Result<SysFileUploadQueryVo> addFileUpload(
            @ModelAttribute @Valid FileUploadParam fileUploadParam
    ) {
        try {
            String token = fileUploadParam.getToken();
            Long userId = fileUploadParam.getUserId();
            MultipartFile multipartFile = fileUploadParam.getFile();
            SysFileUploadQueryVo fileUploadVo = sysFileUploadService.uploadFile(token, multipartFile, userId);
            return Result.success(fileUploadVo);
        } catch (Exception e) {
            return Result.fail(ResultCode.BUSSINESS_ERROR, "上传文件失败");
        }
    }

    /**
     * 修改SysFileUpload表
     */
    @Log(title = "修改SysFileUpload", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysFileUpload对象", notes = "修改SysFileUpload表", response = Result.class)
    public Result<Boolean> updateSysFileUpload(@Valid @RequestBody FileUploadUpdateParam fileUploadUpdateParam) {
        SysFileUpload sysFileUpload = new SysFileUpload();
        sysFileUpload.setRemark(fileUploadUpdateParam.getRemark());
        sysFileUpload.setUpdateTime(LocalDateTime.now());
        LambdaQueryWrapper<SysFileUpload> sysFileUploadLambdaQueryWrapper = Wrappers.<SysFileUpload>lambdaQuery()
                .eq(SysFileUpload::getId, fileUploadUpdateParam.getId());
        boolean flag = sysFileUploadService.update(sysFileUpload, sysFileUploadLambdaQueryWrapper);
        return Result.status(flag);
    }

    /**
     * 删除文件上传数据表
     */
    @Log(title = "删除文件", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除FileUpload对象", notes = "删除文件上传数据表", response = Boolean.class)
    public Result<Boolean> deleteFileByUrl(@Valid @RequestBody SysFileUploadUrlParam sysFileUploadUrlParam) {
        boolean flag = sysFileUploadService.removeFileByUrl(sysFileUploadUrlParam);
        if (!flag) {
            return Result.fail(ResultCode.BUSSINESS_ERROR, "删除失败,该文件可能不存在或者已经被删除");
        }
        return Result.status(flag);
    }

    /**
     * 删除文件上传数据表
     */
    @Log(title = "删除文件", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/id")
    @ApiOperation(value = "删除FileUpload对象", notes = "删除文件上传数据表", response = Boolean.class)
    public Result<Boolean> deleteFileById(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysFileUploadService.removeFileById(idParam);
        if (!flag) {
            return Result.fail(ResultCode.BUSSINESS_ERROR, "删除失败,该文件可能不存在或者已经被删除");
        }
        return Result.status(flag);
    }

    /**
     * 删除文件上传数据表
     */
    @Log(title = "批量删除文件", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "删除FileUpload对象", notes = "批量删除文件上传数据表", response = Boolean.class)
    public Result<Boolean> deleteFileUpload(@Valid @RequestBody IdBatchParam idParam) {
        boolean flag = sysFileUploadService.removeFilesById(idParam);
        if (!flag) {
            return Result.fail(ResultCode.BUSSINESS_ERROR, "删除失败,该文件可能不存在或者已经被删除");
        }
        return Result.status(flag);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
