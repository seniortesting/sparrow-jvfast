package com.jvfast.module.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.demo.model.entity.DemoTest;
import com.jvfast.module.demo.model.param.DemoTestAddParam;
import com.jvfast.module.demo.model.param.DemoTestQueryPageParam;
import com.jvfast.module.demo.model.param.DemoTestUpdateParam;
import com.jvfast.module.demo.model.vo.DemoTestQueryVo;

import java.util.List;

/**
 * <p>
 * 测试表(无用)服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-06-18
*/
public interface DemoTestService extends IService<DemoTest> {


    /**
     * 添加DemoTest实体
     *
     * @param demoTest
     * @return boolean
    */
    boolean saveDemoTest(DemoTestAddParam demoTest);

    /**
     * 通过id更新DemoTest实体
     *
     * @param demoTest
     * @return boolean
    */
    boolean updateDemoTestById(DemoTestUpdateParam demoTest);

    /**
     * 通过id删除DemoTest实体
     *
     * @param idParam
     * @return boolean
    */
    boolean removeDemoTestById(IdParam idParam);

    /**
     * 通过id批量删除DemoTest实体
     *
     * @param idBatchParam
     * @return boolean
    */
    boolean removeDemoTestByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询DemoTest实体
     *
     * @param idParam
     * @return DemoTest
    */
    DemoTest getDemoTestById(IdParam idParam);

    /**
     * 查询DemoTestQueryVo的所有结果
     *
     * @return List<DemoTestQueryVo>
    */
    List<DemoTestQueryVo> listDemoTestQueryVo();

    /**
     * 查询DemoTestQueryVo的分页结果
     *
     * @param demoTestQueryPageParam
     * @return IPage<DemoTestQueryVo>
    */
    IPage<DemoTestQueryVo> pageDemoTestQueryVo(DemoTestQueryPageParam demoTestQueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/

}

