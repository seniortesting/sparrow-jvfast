package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysDictData;
import com.jvfast.module.sys.model.param.SysDictDataAddParam;
import com.jvfast.module.sys.model.param.SysDictDataUpdateParam;
import com.jvfast.module.sys.model.vo.SysDictDataQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysDict对象转换接口
 */
@Mapper
public interface SysDictDataConverter {

    SysDictDataConverter INSTANCE = Mappers.getMapper(SysDictDataConverter.class);

    /**
     * 添加操作
     * SysDictAddParam对象转换为SysDict
     *
     * @param sysDictAddParam
     * @return
     */
    SysDictData convertSysDictAddParam(SysDictDataAddParam sysDictAddParam);

    /**
     * 修改操作
     * SysDictUpdateParam对象转换为SysDict
     *
     * @param sysDictUpdateParam
     * @return
     */
    SysDictData convertSysDictUpdateParam(SysDictDataUpdateParam sysDictUpdateParam);

    /**
     * 查询列表转化实体
     * SysDict对象转换为SysDictQueryVo
     *
     * @param sysDict
     * @return
     */
    List<SysDictDataQueryVo> convertSysDictList(List<SysDictData> sysDict);

    /**
     * 查询分页操作
     * SysDict对象转换为SysDictQueryVo
     *
     * @param sysDict
     * @return
     */
    SysDictDataQueryVo convertSysDict(SysDictData sysDict);


}
