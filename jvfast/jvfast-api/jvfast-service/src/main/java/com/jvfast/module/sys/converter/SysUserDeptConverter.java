package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.param.SysUserDeptAddParam;
import com.jvfast.module.sys.model.param.SysUserDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserDeptQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysUserDept对象转换接口
 */
@Mapper
public interface SysUserDeptConverter {

    SysUserDeptConverter INSTANCE = Mappers.getMapper(SysUserDeptConverter.class);

    /**
     * 添加操作
     * SysUserDeptAddParam对象转换为SysUserDept
     *
     * @param sysUserDeptAddParam
     * @return
     */
    SysUserDept convertSysUserDeptAddParam(SysUserDeptAddParam sysUserDeptAddParam);

    /**
     * 修改操作
     * SysUserDeptUpdateParam对象转换为SysUserDept
     *
     * @param sysUserDeptUpdateParam
     * @return
     */
    SysUserDept convertSysUserDeptUpdateParam(SysUserDeptUpdateParam sysUserDeptUpdateParam);

    /**
     * 查询列表转化实体
     * SysUserDept对象转换为SysUserDeptQueryVo
     *
     * @param sysUserDept
     * @return
     */
    List<SysUserDeptQueryVo> convertSysUserDeptList(List<SysUserDept> sysUserDept);

    /**
     * 查询分页操作
     * SysUserDept对象转换为SysUserDeptQueryVo
     *
     * @param sysUserDept
     * @return
     */
    SysUserDeptQueryVo convertSysUserDept(SysUserDept sysUserDept);


}
