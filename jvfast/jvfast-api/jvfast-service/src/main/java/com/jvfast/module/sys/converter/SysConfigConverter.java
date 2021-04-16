package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysConfig;
import com.jvfast.module.sys.model.param.SysConfigAddParam;
import com.jvfast.module.sys.model.param.SysConfigUpdateParam;
import com.jvfast.module.sys.model.vo.SysConfigQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysConfig对象转换接口
 */
@Mapper
public interface SysConfigConverter {

    SysConfigConverter INSTANCE = Mappers.getMapper(SysConfigConverter.class);

    /**
     * 添加操作
     * SysConfigAddParam对象转换为SysConfig
     *
     * @param sysConfigAddParam
     * @return
     */
    SysConfig convertSysConfigAddParam(SysConfigAddParam sysConfigAddParam);

    /**
     * 修改操作
     * SysConfigUpdateParam对象转换为SysConfig
     *
     * @param sysConfigUpdateParam
     * @return
     */
    SysConfig convertSysConfigUpdateParam(SysConfigUpdateParam sysConfigUpdateParam);

    /**
     * 查询列表转化实体
     * SysConfig对象转换为SysConfigQueryVo
     *
     * @param sysConfig
     * @return
     */
    List<SysConfigQueryVo> convertSysConfigList(List<SysConfig> sysConfig);

    /**
     * 查询分页操作
     * SysConfig对象转换为SysConfigQueryVo
     *
     * @param sysConfig
     * @return
     */
    SysConfigQueryVo convertSysConfig(SysConfig sysConfig);


}
