package com.jvfast.module.ignore.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.ignore.model.entity.PornMaomi;
import com.jvfast.module.ignore.model.param.PornMaomiAddParam;
import com.jvfast.module.ignore.model.param.PornMaomiQueryPageParam;
import com.jvfast.module.ignore.model.param.PornMaomiUpdateParam;
import com.jvfast.module.ignore.model.vo.PornMaomiQueryVo;
import com.jvfast.module.ignore.service.PornMaomiService;
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
 * maomiav.com 前端控制器
 *
 * @author Walter Hu
 * @since 2020-03-14
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "PornMaomi接口")
@RestController
@RequestMapping("/ignore/porn")
public class PornMaomiController {

    private final PornMaomiService pornMaomiService;


    /**
     * 添加maomiav.com
     */
    @Log(title = "添加PornMaomi", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加PornMaomi对象", notes = "添加maomiav.com", response = Result.class)
    public Result<Boolean> addPornMaomi(@Valid @RequestBody PornMaomiAddParam pornMaomiAddParam) {
        boolean flag = pornMaomiService.savePornMaomi(pornMaomiAddParam);
        return Result.status(flag);
    }

    /**
     * 修改maomiav.com
     */
    @Log(title = "修改PornMaomi", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改PornMaomi对象", notes = "修改maomiav.com", response = Result.class)
    public Result<Boolean> updatePornMaomi(@Valid @RequestBody PornMaomiUpdateParam pornMaomiUpdateParam) {
        boolean flag = pornMaomiService.updatePornMaomiById(pornMaomiUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除maomiav.com
     */
    @Log(title = "删除PornMaomi", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除PornMaomi对象", notes = "删除maomiav.com", response = Result.class)
    public Result<Boolean> deletePornMaomi(@Valid @RequestBody IdParam idParam) {
        boolean flag = pornMaomiService.removePornMaomiById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除maomiav.com
     */
    @Log(title = "批量删除PornMaomi", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除PornMaomi对象", notes = "批量删除maomiav.com", response = Result.class)
    public Result<Boolean> deletePornMaomis(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = pornMaomiService.removePornMaomiByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取maomiav.com
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取PornMaomiVo对象详情", notes = "maomiav.com", response = PornMaomi.class)
    public Result<PornMaomi> getPornMaomi(@Valid @RequestBody IdParam idParam) {
        PornMaomi pornMaomi = pornMaomiService.getPornMaomiById(idParam);
        return Result.success(pornMaomi);
    }

    /**
     * PornMaomi查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取PornMaomi列表", notes = "PornMaomi列表", response = PornMaomi.class)
    public Result<List<PornMaomiQueryVo>> listPornMaomi() {
        List<PornMaomiQueryVo> pornMaomiList = pornMaomiService.listPornMaomiQueryVo();
        return Result.success(pornMaomiList);
    }

    /**
     * maomiav.com分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取PornMaomiQueryVo分页列表", notes = "maomiav.com分页列表", response = PornMaomiQueryVo.class)
    public Result<IPage<PornMaomiQueryVo>> pagePornMaomi(@Valid @RequestBody(required = false) PornMaomiQueryPageParam pornMaomiQueryPageParam) {
        IPage<PornMaomiQueryVo> paging = pornMaomiService.pagePornMaomiQueryVo(pornMaomiQueryPageParam);
        return Result.success(paging);
    }
    /***************************** 以下为扩展接口 ******************************************************/

    @PostMapping("/like")
    @ApiOperation(value = "点赞", notes = "点赞列表", response = Boolean.class)
    public Result<Boolean> likePorn(@Valid @RequestBody IdParam idParam){
        boolean result = pornMaomiService.likePorn(idParam);
        return Result.status(result);
    }
}
