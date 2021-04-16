package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysCompany;
import com.jvfast.module.sys.model.param.SysCompanyAddParam;
import com.jvfast.module.sys.model.param.SysCompanyQueryPageParam;
import com.jvfast.module.sys.model.param.SysCompanyUpdateParam;
import com.jvfast.module.sys.model.vo.SysCompanyQueryVo;
import com.jvfast.module.sys.service.SysCompanyService;
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
 * 公司表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-19
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "CvrCompany接口")
@RestController
@RequestMapping("/sys/company")
public class SysCompanyController {

    private final SysCompanyService sysCompanyService;

        /**
    * 添加公司表
    */
    @Log(title = "添加CvrCompany", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加CvrCompany对象", notes = "添加公司表", response = Result.class)
    public Result<Boolean> addCvrCompany(@Valid @RequestBody SysCompanyAddParam cvrCompanyAddParam){
        boolean flag= sysCompanyService.saveCvrCompany(cvrCompanyAddParam);
        return Result.status(flag);
    }
    /**
    * 修改公司表
    */
    @Log(title = "修改CvrCompany", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改CvrCompany对象", notes = "修改公司表", response = Result.class)
    public Result<Boolean> updateCvrCompany(@Valid @RequestBody SysCompanyUpdateParam sysCompanyUpdateParam){
        boolean flag= sysCompanyService.updateCvrCompanyById(sysCompanyUpdateParam);
        return Result.status(flag);
    }
    /**
    * 删除公司表
    */
    @Log(title = "删除CvrCompany", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除CvrCompany对象", notes = "删除公司表", response = Result.class)
    public Result<Boolean> deleteCvrCompany(@Valid @RequestBody IdParam idParam){
        boolean flag= sysCompanyService.removeCvrCompanyById(idParam);
        return Result.status(flag);
    }
    /**
    * 批量删除公司表
    */
    @Log(title = "批量删除CvrCompany", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除CvrCompany对象", notes = "批量删除公司表", response = Result.class)
    public Result<Boolean> deleteCvrCompanys(@RequestBody IdBatchParam IdBatchParam){
        boolean flag= sysCompanyService.removeCvrCompanyByIds(IdBatchParam);
        return Result.status(flag);
    }
    /**
    * 获取公司表
    */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取CvrCompanyVo对象详情", notes = "公司表", response = SysCompany.class)
    public Result<SysCompany> getCvrCompany(@Valid @RequestBody IdParam idParam){
        SysCompany sysCompany = sysCompanyService.getCvrCompanyById(idParam);
        return Result.success(sysCompany);
    }
    /**
    * CvrCompany查询列表
    */
    @PostMapping("/list")
    @ApiOperation(value = "获取CvrCompany列表", notes = "CvrCompany列表", response = SysCompany.class)
    public Result<List<SysCompanyQueryVo>> listCvrCompany()  {
        List<SysCompanyQueryVo> cvrCompanyList = sysCompanyService.listCvrCompanyQueryVo();
        return Result.success(cvrCompanyList);
    }
    /**
    * 公司表分页列表
    */
    @PostMapping("/page")
    @ApiOperation(value = "获取CvrCompanyQueryVo分页列表", notes = "公司表分页列表", response = SysCompanyQueryVo.class)
    public Result<IPage<SysCompanyQueryVo>> pageCvrCompany(@Valid @RequestBody(required = false) SysCompanyQueryPageParam sysCompanyQueryPageParam) {
        IPage<SysCompanyQueryVo> paging = sysCompanyService.pageCvrCompanyQueryVo(sysCompanyQueryPageParam);
        return Result.success(paging);
    }
        /***************************** 以下为扩展接口 ******************************************************/
}
