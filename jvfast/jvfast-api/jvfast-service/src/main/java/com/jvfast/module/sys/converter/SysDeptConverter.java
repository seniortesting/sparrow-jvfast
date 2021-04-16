package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysDept;
import com.jvfast.module.sys.model.param.SysDeptAddParam;
import com.jvfast.module.sys.model.param.SysDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysDeptQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysDept对象转换接口
 */
@Mapper
public interface SysDeptConverter {

    SysDeptConverter INSTANCE = Mappers.getMapper(SysDeptConverter.class);

    /**
     * 添加操作
     * SysDeptAddParam对象转换为SysDept
     *
     * @param sysDeptAddParam
     * @return
     */
    SysDept convertSysDeptAddParam(SysDeptAddParam sysDeptAddParam);

    /**
     * 修改操作
     * SysDeptUpdateParam对象转换为SysDept
     *
     * @param sysDeptUpdateParam
     * @return
     */
    SysDept convertSysDeptUpdateParam(SysDeptUpdateParam sysDeptUpdateParam);

    /**
     * 查询列表转化实体
     * SysDept对象转换为SysDeptQueryVo
     *
     * @param sysDept
     * @return
     */
    List<SysDeptQueryVo> convertSysDeptList(List<SysDept> sysDept);

    /**
     * 查询分页操作
     * SysDept对象转换为SysDeptQueryVo
     *
     * @param sysDept
     * @return
     */
    SysDeptQueryVo convertSysDept(SysDept sysDept);


}
