package com.jvfast.module.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.demo.converter.DemoTestConverter;
import com.jvfast.module.demo.mapper.DemoTestMapper;
import com.jvfast.module.demo.model.entity.DemoTest;
import com.jvfast.module.demo.model.param.DemoTestAddParam;
import com.jvfast.module.demo.model.param.DemoTestQueryPageParam;
import com.jvfast.module.demo.model.param.DemoTestUpdateParam;
import com.jvfast.module.demo.model.vo.DemoTestQueryVo;
import com.jvfast.module.demo.service.DemoTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 测试表(无用)服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-06-18
*/
@RequiredArgsConstructor
@Service
@Slf4j
public class DemoTestServiceImpl extends ServiceImpl<DemoTestMapper, DemoTest>implements DemoTestService {
    private final DemoTestMapper demoTestMapper;

    /**
     * 添加DemoTest实体
     *
     * @param demoTestAddParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean saveDemoTest(DemoTestAddParam demoTestAddParam) {
        // 对象转换
        DemoTest demoTest = DemoTestConverter.INSTANCE.convertDemoTestAddParam(demoTestAddParam);
        boolean savedResult = save(demoTest);
        return savedResult;
    }
    /**
     * 通过id更新DemoTest实体
     *
     * @param demoTestUpdateParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean updateDemoTestById(DemoTestUpdateParam demoTestUpdateParam) {
        //对象转换
        DemoTest demoTest = DemoTestConverter.INSTANCE.convertDemoTestUpdateParam(demoTestUpdateParam);
        boolean updatedResult = updateById(demoTest);
        return updatedResult;
    }
    /**
     * 通过id删除DemoTest实体
     *
     * @param idParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean removeDemoTestById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }
    /**
     * 通过id批量删除DemoTest实体
     *
     * @param idBatchParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean removeDemoTestByIds(IdBatchParam idBatchParam) {
        List<Long>ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }
    /**
     * 通过id查询DemoTest实体
     *
     * @param idParam
     * @return DemoTest
    */
    @Override
    public DemoTest getDemoTestById(IdParam idParam) {
        Long id = idParam.getId();
        DemoTest getResult = getById(id);
        return getResult;
    }
    /**
     * 查询DemoTestQueryVo的所有结果
     *
     * @return List<DemoTestQueryVo>
    */
    @Override
    public List<DemoTestQueryVo> listDemoTestQueryVo() {
        LambdaQueryWrapper<DemoTest> lambdaQueryWrapper = Wrappers.<DemoTest>lambdaQuery();
        List<DemoTest>listdemoTest = list(lambdaQueryWrapper);
        List<DemoTestQueryVo> listDemoTestQueryVo = DemoTestConverter.INSTANCE.convertDemoTestList(listdemoTest);
        return listDemoTestQueryVo;
    }
    /**
     * 查询DemoTestQueryVo的分页结果
     *
     * @param demoTestQueryPageParam
     * @return IPage<DemoTestQueryVo>
    */
    @Override
    public IPage<DemoTestQueryVo> pageDemoTestQueryVo(DemoTestQueryPageParam demoTestQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = demoTestQueryPageParam.getPageNo();
        Integer pageSize = demoTestQueryPageParam.getPageSize();
        String keyword = demoTestQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo,pageSize,true);
        // 查询分页结果
        List<DemoTestQueryVo> listDemoTestQueryVo = demoTestMapper.pageDemoTest(pagingData, demoTestQueryPageParam);
        pagingData.setRecords(listDemoTestQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
