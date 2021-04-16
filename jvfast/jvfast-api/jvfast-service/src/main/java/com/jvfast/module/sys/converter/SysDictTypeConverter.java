package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysDictType;
import com.jvfast.module.sys.model.param.SysDictTypeAddParam;
import com.jvfast.module.sys.model.param.SysDictTypeUpdateParam;
import com.jvfast.module.sys.model.vo.SysDictTypeQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysDictType对象转换接口
 */
@Mapper
public interface SysDictTypeConverter {

    SysDictTypeConverter INSTANCE = Mappers.getMapper(SysDictTypeConverter.class);

    /**
     * 添加操作
     * SysDictTypeAddParam对象转换为SysDictType
     *
     * @param sysDictTypeAddParam
     * @return
     */
    SysDictType convertSysDictTypeAddParam(SysDictTypeAddParam sysDictTypeAddParam);

    /**
     * 修改操作
     * SysDictTypeUpdateParam对象转换为SysDictType
     *
     * @param sysDictTypeUpdateParam
     * @return
     */
    SysDictType convertSysDictTypeUpdateParam(SysDictTypeUpdateParam sysDictTypeUpdateParam);

    /**
     * 查询列表转化实体
     * SysDictType对象转换为SysDictTypeQueryVo
     *
     * @param sysDictType
     * @return
     */
    List<SysDictTypeQueryVo> convertSysDictTypeList(List<SysDictType> sysDictType);

    /**
     * 查询分页操作
     * SysDictType对象转换为SysDictTypeQueryVo
     *
     * @param sysDictType
     * @return
     */
    SysDictTypeQueryVo convertSysDictType(SysDictType sysDictType);


}
