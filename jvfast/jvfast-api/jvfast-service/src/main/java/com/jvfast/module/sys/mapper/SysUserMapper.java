package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.dto.SysUserDeptDTO;
import com.jvfast.module.sys.model.dto.SysUserRoleDTO;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.param.SysUserQueryPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户信息表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * <p>
     * sys_user分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-18
     */
    List<SysUserDeptDTO> getSysUserPage(IPage page, @Param("query") SysUserQueryPageParam queryPageParam, @Param("deptIds") List<Long> deptIds);

    /***************************** 以下为扩展接口 ******************************************************/
    List<SysUserDeptDTO> getSysUserById(@Param("query") IdParam idParam);

    List<SysUserRoleDTO> getSysUserRoleByUserId(@Param("query") IdParam idParam);

}
