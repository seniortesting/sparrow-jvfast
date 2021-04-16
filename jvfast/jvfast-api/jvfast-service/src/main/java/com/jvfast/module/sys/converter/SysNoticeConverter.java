package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysNotice;
import com.jvfast.module.sys.model.param.SysNoticeAddParam;
import com.jvfast.module.sys.model.param.SysNoticeUpdateParam;
import com.jvfast.module.sys.model.vo.SysNoticeQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysNotice对象转换接口
 */
@Mapper
public interface SysNoticeConverter {

    SysNoticeConverter INSTANCE = Mappers.getMapper(SysNoticeConverter.class);

    /**
     * 添加操作
     * SysNoticeAddParam对象转换为SysNotice
     *
     * @param sysNoticeAddParam
     * @return
     */
    SysNotice convertSysNoticeAddParam(SysNoticeAddParam sysNoticeAddParam);

    /**
     * 修改操作
     * SysNoticeUpdateParam对象转换为SysNotice
     *
     * @param sysNoticeUpdateParam
     * @return
     */
    SysNotice convertSysNoticeUpdateParam(SysNoticeUpdateParam sysNoticeUpdateParam);

    /**
     * 查询列表转化实体
     * SysNotice对象转换为SysNoticeQueryVo
     *
     * @param sysNotice
     * @return
     */
    List<SysNoticeQueryVo> convertSysNoticeList(List<SysNotice> sysNotice);

    /**
     * 查询分页操作
     * SysNotice对象转换为SysNoticeQueryVo
     *
     * @param sysNotice
     * @return
     */
    SysNoticeQueryVo convertSysNotice(SysNotice sysNotice);


}
