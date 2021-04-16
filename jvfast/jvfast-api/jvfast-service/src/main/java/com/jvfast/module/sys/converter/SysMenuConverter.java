package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysMenu;
import com.jvfast.module.sys.model.param.SysMenuAddParam;
import com.jvfast.module.sys.model.param.SysMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysMenu对象转换接口
 */
@Mapper
public interface SysMenuConverter {

    SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);

    /**
     * 添加操作
     * SysMenuAddParam对象转换为SysMenu
     *
     * @param sysMenuAddParam
     * @return
     */
    SysMenu convertSysMenuAddParam(SysMenuAddParam sysMenuAddParam);

    /**
     * 修改操作
     * SysMenuUpdateParam对象转换为SysMenu
     *
     * @param sysMenuUpdateParam
     * @return
     */
    SysMenu convertSysMenuUpdateParam(SysMenuUpdateParam sysMenuUpdateParam);

    /**
     * 查询列表转化实体
     * SysMenu对象转换为SysMenuQueryVo
     *
     * @param sysMenu
     * @return
     */
    List<SysMenuQueryVo> convertSysMenuQueryList(List<SysMenu> sysMenu);

    /**
     * 查询分页操作
     * SysMenu对象转换为SysMenuQueryVo
     *
     * @param sysMenu
     * @return
     */
    SysMenuQueryVo convertSysMenu(SysMenu sysMenu);

    SysMenu convertSysMenu(SysMenuQueryVo sysMenu);

    List<SysMenu> convertSysMenuList(List<SysMenuQueryVo> sysMenu);
}
