package com.jvfast.module.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.demo.model.entity.DemoTest;
import com.jvfast.module.demo.model.param.DemoTestAddParam;
import com.jvfast.module.demo.model.param.DemoTestQueryPageParam;
import com.jvfast.module.demo.model.param.DemoTestUpdateParam;
import com.jvfast.module.demo.model.vo.DemoTestQueryVo;
import com.jvfast.module.demo.service.DemoTestService;
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
* 测试表(无用)前端路由控制器
*
* @author Walter Hu
* @since 2020-06-18
*/
@RequiredArgsConstructor
@Slf4j
@Api(tags = "DemoTest接口")
@RestController
@RequestMapping("/demo/test")
public class DemoTestController {

    private final DemoTestService demoTestService;


    /**
    * 添加测试表(无用)
    */
    @Log(title = "添加DemoTest", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加DemoTest对象", notes = "添加测试表(无用)", response = Result.class)
    public Result<Boolean> addDemoTest(@Valid @RequestBody DemoTestAddParam demoTestAddParam) {
        boolean flag = demoTestService.saveDemoTest(demoTestAddParam);
        return Result.status(flag);
    }

    /**
    * 修改测试表(无用)
    */
    @Log(title = "修改DemoTest", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改DemoTest对象", notes = "修改测试表(无用)", response = Result.class)
    public Result<Boolean> updateDemoTest(@Valid @RequestBody DemoTestUpdateParam demoTestUpdateParam) {
        boolean flag = demoTestService.updateDemoTestById(demoTestUpdateParam);
        return Result.status(flag);
    }

    /**
    * 删除测试表(无用)
    */
    @Log(title = "删除DemoTest", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除DemoTest对象", notes = "删除测试表(无用)", response = Result.class)
    public Result<Boolean> deleteDemoTest(@Valid @RequestBody IdParam idParam) {
        boolean flag = demoTestService.removeDemoTestById(idParam);
        return Result.status(flag);
    }

    /**
    * 批量删除测试表(无用)
    */
    @Log(title = "批量删除DemoTest", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除DemoTest对象", notes = "批量删除测试表(无用)", response = Result.class)
    public Result<Boolean> deleteDemoTests(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = demoTestService.removeDemoTestByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
    * 获取测试表(无用)
    */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取DemoTestVo对象详情", notes = "测试表(无用)", response = DemoTest.class)
    public Result<DemoTest> getDemoTest(@Valid @RequestBody IdParam idParam) {
        DemoTest demoTest = demoTestService.getDemoTestById(idParam);
        return Result.success(demoTest);
    }

    /**
    * DemoTest查询列表
    */
    @PostMapping("/list")
    @ApiOperation(value = "获取DemoTest列表", notes = "DemoTest列表", response = DemoTest.class)
    public Result<List<DemoTestQueryVo>> listDemoTest() {
        List<DemoTestQueryVo> demoTestList = demoTestService.listDemoTestQueryVo();
        return Result.success(demoTestList);
    }

    /**
    * 测试表(无用)分页列表
    */
    @PostMapping("/page")
    @ApiOperation(value = "获取DemoTestQueryVo分页列表", notes = "测试表(无用)分页列表", response = DemoTestQueryVo.class)
    public Result<IPage<DemoTestQueryVo>> pageDemoTest(@Valid @RequestBody(required = false) DemoTestQueryPageParam demoTestQueryPageParam) {
        IPage<DemoTestQueryVo> paging = demoTestService.pageDemoTestQueryVo(demoTestQueryPageParam);
        return Result.success(paging);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
