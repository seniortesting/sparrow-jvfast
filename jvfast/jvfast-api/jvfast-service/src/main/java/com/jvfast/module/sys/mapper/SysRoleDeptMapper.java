package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysRoleDept;
import com.jvfast.module.sys.model.param.SysRoleDeptQueryPageParam;
import com.jvfast.module.sys.model.vo.SysRoleDeptQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 角色部门表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

    /**
     * <p>
     * sys_role_dept分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysRoleDeptQueryVo> getSysRoleDeptPage(IPage page, @Param("query") SysRoleDeptQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
