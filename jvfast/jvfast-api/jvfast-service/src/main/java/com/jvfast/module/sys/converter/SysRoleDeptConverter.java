package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysRoleDept;
import com.jvfast.module.sys.model.param.SysRoleDeptAddParam;
import com.jvfast.module.sys.model.param.SysRoleDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleDeptQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysRoleDept对象转换接口
 */
@Mapper
public interface SysRoleDeptConverter {

    SysRoleDeptConverter INSTANCE = Mappers.getMapper(SysRoleDeptConverter.class);

    /**
     * 添加操作
     * SysRoleDeptAddParam对象转换为SysRoleDept
     *
     * @param sysRoleDeptAddParam
     * @return
     */
    SysRoleDept convertSysRoleDeptAddParam(SysRoleDeptAddParam sysRoleDeptAddParam);

    /**
     * 修改操作
     * SysRoleDeptUpdateParam对象转换为SysRoleDept
     *
     * @param sysRoleDeptUpdateParam
     * @return
     */
    SysRoleDept convertSysRoleDeptUpdateParam(SysRoleDeptUpdateParam sysRoleDeptUpdateParam);

    /**
     * 查询列表转化实体
     * SysRoleDept对象转换为SysRoleDeptQueryVo
     *
     * @param sysRoleDept
     * @return
     */
    List<SysRoleDeptQueryVo> convertSysRoleDeptList(List<SysRoleDept> sysRoleDept);

    /**
     * 查询分页操作
     * SysRoleDept对象转换为SysRoleDeptQueryVo
     *
     * @param sysRoleDept
     * @return
     */
    SysRoleDeptQueryVo convertSysRoleDept(SysRoleDept sysRoleDept);


}
