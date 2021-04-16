package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.param.SysUserDeptQueryPageParam;
import com.jvfast.module.sys.model.vo.SysUserDeptQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 用户部门表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * <p>
     * sys_user_dept分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysUserDeptQueryVo> getSysUserDeptPage(IPage page, @Param("query") SysUserDeptQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
