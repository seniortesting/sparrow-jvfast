package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysCity;
import com.jvfast.module.sys.model.param.SysCityAddParam;
import com.jvfast.module.sys.model.param.SysCityUpdateParam;
import com.jvfast.module.sys.model.vo.SysCityQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysCity对象转换接口
 * @author Walter Hu
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface SysCityConverter {

    SysCityConverter INSTANCE = Mappers.getMapper(SysCityConverter.class);

    /**
     *  添加操作
     *  <p>
     *  SysCityAddParam对象转换为SysCity
     *
     * @param sysCityAddParam
     * @return
     */
    SysCity convertSysCityAddParam(SysCityAddParam sysCityAddParam);

    /**
     *  修改操作
     *  <p>
     *  SysCityUpdateParam对象转换为SysCity
     *
     * @param sysCityUpdateParam
     * @return
     */
    SysCity convertSysCityUpdateParam(SysCityUpdateParam sysCityUpdateParam);

    /**
     *  查询列表转化实体
     *  <p>
     *  SysCity对象转换为SysCityQueryVo
     *
     * @param sysCity
     * @return
     */
    List<SysCityQueryVo> convertSysCityList(List<SysCity> sysCity);

    /**
     *  查询分页操作
     *  <p>
     *  SysCity对象转换为SysCityQueryVo
     *
     * @param sysCity
     * @return
     */
    SysCityQueryVo convertSysCity(SysCity sysCity);

}
