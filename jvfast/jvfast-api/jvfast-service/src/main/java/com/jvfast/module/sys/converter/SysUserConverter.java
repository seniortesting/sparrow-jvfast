package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.param.SysUserAddParam;
import com.jvfast.module.sys.model.param.SysUserUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysUser对象转换接口
 */
@Mapper
public interface SysUserConverter {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    SysUser convertSysUserAddParam(SysUserAddParam sysUserAddParam);

    /**
     * 修改操作
     * SysUserUpdateParam对象转换为SysUser
     *
     * @param sysUserUpdateParam
     * @return
     */
    SysUser convertSysUserUpdateParam(SysUserUpdateParam sysUserUpdateParam);

    /**
     * 查询列表转化实体
     * SysUser对象转换为SysUserQueryVo
     *
     * @param sysUser
     * @return
     */
    List<SysUserQueryVo> convertSysUserList(List<SysUser> sysUser);

    /**
     * 查询分页操作
     * SysUser对象转换为SysUserQueryVo
     *
     * @param sysUser
     * @return
     */
    SysUserQueryVo convertSysUser(SysUser sysUser);


}
