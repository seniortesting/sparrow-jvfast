package com.jvfast.module.demo.converter;

import com.jvfast.module.demo.model.entity.DemoTest;
import com.jvfast.module.demo.model.param.DemoTestAddParam;
import com.jvfast.module.demo.model.param.DemoTestUpdateParam;
import com.jvfast.module.demo.model.vo.DemoTestQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * DemoTest对象转换接口
 * @author Walter Hu
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface DemoTestConverter {

    DemoTestConverter INSTANCE = Mappers.getMapper(DemoTestConverter.class);

    /**
     *  添加操作
     *  DemoTestAddParam对象转换为DemoTest
     *
     * @param demoTestAddParam
     * @return DemoTest
     */
    DemoTest convertDemoTestAddParam(DemoTestAddParam demoTestAddParam);

    /**
     *  修改操作
     *  DemoTestUpdateParam对象转换为DemoTest
     *
     * @param demoTestUpdateParam
     * @return DemoTest
     */
    DemoTest convertDemoTestUpdateParam(DemoTestUpdateParam demoTestUpdateParam);

    /**
     *  查询列表转化实体
     *  DemoTest对象转换为DemoTestQueryVo
     *
     * @param demoTest
     * @return List<DemoTestQueryVo>
     */
    List<DemoTestQueryVo> convertDemoTestList(List<DemoTest> demoTest);

    /**
     *  查询分页操作
     *  DemoTest对象转换为DemoTestQueryVo
     *
     * @param demoTest
     * @return DemoTestQueryVo
     */
    DemoTestQueryVo convertDemoTest(DemoTest demoTest);

}
