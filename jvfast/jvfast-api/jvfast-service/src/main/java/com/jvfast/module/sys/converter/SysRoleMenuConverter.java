package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysRoleMenu;
import com.jvfast.module.sys.model.param.SysRoleMenuAddParam;
import com.jvfast.module.sys.model.param.SysRoleMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleMenuQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysRoleMenu对象转换接口
 */
@Mapper
public interface SysRoleMenuConverter {

    SysRoleMenuConverter INSTANCE = Mappers.getMapper(SysRoleMenuConverter.class);

    /**
     * 添加操作
     * SysRoleMenuAddParam对象转换为SysRoleMenu
     *
     * @param sysRoleMenuAddParam
     * @return
     */
    SysRoleMenu convertSysRoleMenuAddParam(SysRoleMenuAddParam sysRoleMenuAddParam);

    /**
     * 修改操作
     * SysRoleMenuUpdateParam对象转换为SysRoleMenu
     *
     * @param sysRoleMenuUpdateParam
     * @return
     */
    SysRoleMenu convertSysRoleMenuUpdateParam(SysRoleMenuUpdateParam sysRoleMenuUpdateParam);

    /**
     * 查询列表转化实体
     * SysRoleMenu对象转换为SysRoleMenuQueryVo
     *
     * @param sysRoleMenu
     * @return
     */
    List<SysRoleMenuQueryVo> convertSysRoleMenuList(List<SysRoleMenu> sysRoleMenu);

    /**
     * 查询分页操作
     * SysRoleMenu对象转换为SysRoleMenuQueryVo
     *
     * @param sysRoleMenu
     * @return
     */
    SysRoleMenuQueryVo convertSysRoleMenu(SysRoleMenu sysRoleMenu);


}
