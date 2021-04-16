package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysRole;
import com.jvfast.module.sys.model.param.SysRoleAddParam;
import com.jvfast.module.sys.model.param.SysRoleUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysRole对象转换接口
 */
@Mapper
public interface SysRoleConverter {

    SysRoleConverter INSTANCE = Mappers.getMapper(SysRoleConverter.class);

    /**
     * 添加操作
     * AuthRoleAddParam对象转换为AuthRole
     *
     * @param authRoleAddParam
     * @return
     */
    SysRole convertAuthRoleAddParam(SysRoleAddParam authRoleAddParam);

    /**
     * 修改操作
     * AuthRoleUpdateParam对象转换为AuthRole
     *
     * @param authRoleUpdateParam
     * @return
     */
    SysRole convertAuthRoleUpdateParam(SysRoleUpdateParam authRoleUpdateParam);

    /**
     * 查询列表转化实体
     * AuthRole对象转换为AuthRoleQueryVo
     *
     * @param authRole
     * @return
     */
    List<SysRoleQueryVo> convertAuthRoleList(List<SysRole> authRole);

    /**
     * 查询分页操作
     * AuthRole对象转换为AuthRoleQueryVo
     *
     * @param authRole
     * @return
     */
    SysRoleQueryVo convertAuthRole(SysRole authRole);


}
