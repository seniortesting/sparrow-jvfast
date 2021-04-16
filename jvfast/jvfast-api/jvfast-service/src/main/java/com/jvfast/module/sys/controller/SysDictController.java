package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysDictData;
import com.jvfast.module.sys.model.entity.SysDictType;
import com.jvfast.module.sys.model.param.*;
import com.jvfast.module.sys.model.vo.SysDictDataQueryVo;
import com.jvfast.module.sys.model.vo.SysDictTypeQueryVo;
import com.jvfast.module.sys.service.SysDictDataService;
import com.jvfast.module.sys.service.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统字典表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysDict接口")
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    private final SysDictDataService sysDictDataService;
    private final SysDictTypeService sysDictTypeService;

    /**
     * 添加系统字典表
     */
    @Log(title = "新增字典类型", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add/type")
    @ApiOperation(value = "添加SysDictType对象", notes = "添加系统字典表", response = Result.class)
    public Result<Boolean> addSysDictType(@Valid @RequestBody SysDictTypeAddParam sysDictTypeAddParam) {
        boolean flag = sysDictTypeService.saveSysDictType(sysDictTypeAddParam);
        return Result.status(flag);
    }

    @Log(title = "新增字典数据", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add/data")
    @ApiOperation(value = "添加SysDictData对象", notes = "添加系统字典表", response = Result.class)
    public Result<Boolean> addSysDictData(@Valid @RequestBody SysDictDataAddParam sysDictAddParam) {
        boolean flag = sysDictDataService.saveSysDictData(sysDictAddParam);
        return Result.status(flag);
    }

    /**
     * 修改系统字典表
     */
    @Log(title = "修改字典类型数据", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update/type")
    @ApiOperation(value = "修改SysDictType对象", notes = "修改系统字典表", response = Result.class)
    public Result<Boolean> updateSysDictType(@Valid @RequestBody SysDictTypeUpdateParam sysDictUpdateParam) {
        boolean flag = sysDictTypeService.updateSysDictTypeById(sysDictUpdateParam);
        return Result.status(flag);
    }

    @Log(title = "修改字典数据", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update/data")
    @ApiOperation(value = "修改SysDictData对象", notes = "修改系统字典表", response = Result.class)
    public Result<Boolean> updateSysDictData(@Valid @RequestBody SysDictDataUpdateParam sysDictUpdateParam) {
        boolean flag = sysDictDataService.updateSysDictDataById(sysDictUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除系统字典表
     */
    @Log(title = "删除字典类型", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del/type")
    @ApiOperation(value = "删除SysDictType对象", notes = "删除系统字典表", response = Result.class)
    public Result<Boolean> deleteSysDictType(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysDictTypeService.removeSysDictTypeById(idParam);
        return Result.status(flag);
    }

    @Log(title = "删除字典数据", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del/data")
    @ApiOperation(value = "删除SysDict对象", notes = "删除系统字典表", response = Result.class)
    public Result<Boolean> deleteSysDictData(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysDictDataService.removeSysDictDataById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除字典表
     */
    @Log(title = "批量删除字典类型", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch/type")
    @ApiOperation(value = "批量删除SysDictType对象", notes = "批量删除字典表", response = Result.class)
    public Result<Boolean> deleteSysDictTypes(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysDictTypeService.removeSysDictTypesById(idBatchParam);
        return Result.status(flag);
    }

    @Log(title = "批量删除字典数据", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch/data")
    @ApiOperation(value = "批量删除SysDictData对象", notes = "批量删除字典表", response = Result.class)
    public Result<Boolean> deleteSysDictDatas(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysDictDataService.removeSysDictDatasById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取系统字典表
     */
    @PostMapping("/id/type")
    @ApiOperation(value = "根据id获取SysDictTypeVo对象详情", notes = "系统字典表", response = SysDictType.class)
    public Result<SysDictType> getSysDictType(@Valid @RequestBody IdParam idParam) {
        SysDictType sysDict = sysDictTypeService.getSysDictTypeById(idParam);
        return Result.success(sysDict);
    }

    @PostMapping("/id/data")
    @ApiOperation(value = "根据id获取SysDictDataVo对象详情", notes = "系统字典表", response = SysDictData.class)
    public Result<SysDictData> getSysDictData(@Valid @RequestBody IdParam idParam) {
        SysDictData sysDict = sysDictDataService.getSysDictDataById(idParam);
        return Result.success(sysDict);
    }

    /**
     * SysDict查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysDictType列表", notes = "SysDictType列表", response = SysDictData.class)
    public Result<List<SysDictTypeQueryVo>> getSysDictList() {
        List<SysDictTypeQueryVo> sysDictList = sysDictTypeService.listSysDictTypeQueryVo();
        return Result.success(sysDictList);
    }


    /**
     * 系统字典表分页列表
     */
    @PostMapping("/page/type")
    @ApiOperation(value = "获取SysDictQueryVo分页列表", notes = "系统字典表分页列表", response = SysDictTypeQueryVo.class)
    public Result<IPage<SysDictTypeQueryVo>> getSysDictTypePageList(@Valid @RequestBody(required = false) SysDictTypeQueryPageParam sysDictTypeQueryPageParam) throws DataAccessException {
        IPage<SysDictTypeQueryVo> paging = sysDictTypeService.pageSysDictTypeQueryVo(sysDictTypeQueryPageParam);
        return Result.success(paging);
    }

    /**
     * 系统字典表分页列表
     */
    @PostMapping("/page/data")
    @ApiOperation(value = "获取SysDictQueryVo分页列表", notes = "系统字典表分页列表", response = SysDictDataQueryVo.class)
    public Result<IPage<SysDictDataQueryVo>> getSysDictPageList(@Valid @RequestBody(required = false) SysDictDataQueryPageParam sysDictQueryPageParam) throws DataAccessException {
        IPage<SysDictDataQueryVo> paging = sysDictDataService.pageSysDictDataQueryVo(sysDictQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
