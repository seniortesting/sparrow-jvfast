package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysPost;
import com.jvfast.module.sys.model.param.SysPostAddParam;
import com.jvfast.module.sys.model.param.SysPostQueryPageParam;
import com.jvfast.module.sys.model.param.SysPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysPostQueryVo;
import com.jvfast.module.sys.service.SysPostService;
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
 * 岗位表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysPost接口")
@RestController
@RequestMapping("/sys/post")
public class SysPostController {
    private final SysPostService sysPostService;


    /**
     * 添加岗位表
     */
    @Log(title = "新增岗位", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysPost对象", notes = "添加岗位表", response = Result.class)
    public Result<Boolean> addSysPost(@Valid @RequestBody SysPostAddParam sysJobAddParam) {
        boolean flag = sysPostService.saveSysPost(sysJobAddParam);
        return Result.status(flag);
    }

    /**
     * 修改岗位表
     */
    @Log(title = "修改岗位", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysPost对象", notes = "修改岗位表", response = Result.class)
    public Result<Boolean> updateSysPost(@Valid @RequestBody SysPostUpdateParam sysPostUpdateParam) {
        boolean flag = sysPostService.updateSysPostById(sysPostUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除岗位表
     */
    @Log(title = "删除岗位", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysPost对象", notes = "删除岗位表", response = Result.class)
    public Result<Boolean> deleteSysPost(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysPostService.removeSysPostById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除岗位表
     */
    @Log(title = "批量删除岗位", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysPost对象", notes = "批量删除岗位表", response = Result.class)
    public Result<Boolean> deleteSysPosts(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysPostService.removeSysPostsById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取岗位表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysPostVo对象详情", notes = "岗位表", response = SysPost.class)
    public Result<SysPost> getSysPost(@Valid @RequestBody IdParam idParam) {
        SysPost sysPost = sysPostService.getSysPostById(idParam);
        return Result.success(sysPost);
    }

    /**
     * SysPost查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysPost列表", notes = "SysPost列表", response = SysPost.class)
    public Result<List<SysPostQueryVo>> getSysPostList() {
        List<SysPostQueryVo> sysJobList = sysPostService.listSysPostQueryVo();
        return Result.success(sysJobList);
    }


    /**
     * 岗位表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysPostQueryVo分页列表", notes = "岗位表分页列表", response = SysPostQueryVo.class)
    public Result<IPage<SysPostQueryVo>> getSysPostPageList(@Valid @RequestBody(required = false) SysPostQueryPageParam sysPostQueryPageParam) throws DataAccessException {
        IPage<SysPostQueryVo> paging = sysPostService.pageSysPostQueryVo(sysPostQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
