package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysDept;
import com.jvfast.module.sys.model.param.SysDeptAddParam;
import com.jvfast.module.sys.model.param.SysDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysDeptQueryVo;
import com.jvfast.module.sys.service.SysDeptService;
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
 * 部门表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysDept接口")
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
    private final SysDeptService sysDeptService;


    /**
     * 添加部门表
     */
    @Log(title = "新增部门", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysDept对象", notes = "添加部门表", response = Result.class)
    public Result<Boolean> addSysDept(@Valid @RequestBody SysDeptAddParam sysDeptAddParam) {
        boolean flag = sysDeptService.saveSysDept(sysDeptAddParam);
        return Result.status(flag);
    }

    /**
     * 修改部门表
     */
    @Log(title = "修改部门", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysDept对象", notes = "修改部门表", response = Result.class)
    public Result<Boolean> updateSysDept(@Valid @RequestBody SysDeptUpdateParam sysDeptUpdateParam) {
        boolean flag = sysDeptService.updateSysDeptById(sysDeptUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除部门表
     */
    @Log(title = "删除部门", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysDept对象", notes = "删除部门表", response = Result.class)
    public Result<Boolean> deleteSysDept(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysDeptService.removeSysDeptById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除部门表
     */
    @Log(title = "批量删除部门", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysDept对象", notes = "批量删除部门表", response = Result.class)
    public Result<Boolean> deleteSysDept(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysDeptService.removeSysDeptsById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取部门表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysDeptVo对象详情", notes = "部门表", response = SysDept.class)
    public Result<SysDept> getSysDept(@Valid @RequestBody IdParam idParam) {
        SysDept sysDept = sysDeptService.getSysDeptById(idParam);
        return Result.success(sysDept);
    }

    /**
     * SysDept查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysDept列表", notes = "SysDept列表", response = SysDept.class)
    public Result<List<SysDeptQueryVo>> getSysDeptList() {
        List<SysDeptQueryVo> sysDeptList = sysDeptService.listSysDeptQueryVo();
        return Result.success(sysDeptList);
    }


    /**
     * 部门表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysDeptQueryVo分页列表", notes = "部门表分页列表", response = SysDeptQueryVo.class)
    public Result<IPage<SysDeptQueryVo>> getSysDeptPageList(@Valid @RequestBody(required = false) SysDeptQueryPageParam sysDeptQueryPageParam) throws DataAccessException {
        IPage<SysDeptQueryVo> paging = sysDeptService.pageSysDeptQueryVo(sysDeptQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @PostMapping("/tree")
    @ApiOperation(value = "根据id获取SysDeptVo树形结构", notes = "部门树形结构", response = SysDeptQueryVo.class)
    public Result<List<SysDeptQueryVo>> getDeptTree() {
        List<SysDeptQueryVo> deptTreeList = sysDeptService.getDeptTreeList();
        return Result.success(deptTreeList);
    }
}
