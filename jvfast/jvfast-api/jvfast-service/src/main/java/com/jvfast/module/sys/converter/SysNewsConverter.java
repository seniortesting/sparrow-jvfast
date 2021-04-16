package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysNews;
import com.jvfast.module.sys.model.param.SysNewsAddParam;
import com.jvfast.module.sys.model.param.SysNewsUpdateParam;
import com.jvfast.module.sys.model.vo.SysNewsQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysNews对象转换接口
 */
@Mapper
public interface SysNewsConverter {

    SysNewsConverter INSTANCE = Mappers.getMapper(SysNewsConverter.class);

    /**
     * 添加操作
     * SysNewsAddParam对象转换为SysNews
     *
     * @param sysNewsAddParam
     * @return
     */
    SysNews convertSysNewsAddParam(SysNewsAddParam sysNewsAddParam);

    /**
     * 修改操作
     * SysNewsUpdateParam对象转换为SysNews
     *
     * @param sysNewsUpdateParam
     * @return
     */
    SysNews convertSysNewsUpdateParam(SysNewsUpdateParam sysNewsUpdateParam);

    /**
     * 查询列表转化实体
     * SysNews对象转换为SysNewsQueryVo
     *
     * @param sysNews
     * @return
     */
    List<SysNewsQueryVo> convertSysNewsList(List<SysNews> sysNews);

    /**
     * 查询分页操作
     * SysNews对象转换为SysNewsQueryVo
     *
     * @param sysNews
     * @return
     */
    SysNewsQueryVo convertSysNews(SysNews sysNews);


}
