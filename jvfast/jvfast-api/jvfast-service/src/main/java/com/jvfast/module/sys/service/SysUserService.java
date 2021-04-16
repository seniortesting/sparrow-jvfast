package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.dto.SysUserDeptDTO;
import com.jvfast.module.sys.model.dto.SysUserRoleDTO;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.param.SysUserAddParam;
import com.jvfast.module.sys.model.param.SysUserQueryPageParam;
import com.jvfast.module.sys.model.param.SysUserUpdateParam;

import java.util.List;

/**
 * <p>
 * 系统用户信息表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
public interface SysUserService extends IService<SysUser> {

    boolean addSysUser(SysUserAddParam sysUserAddParam);

    /**
     * 通过id更新SysUser
     *
     * @param sysUser
     * @return
     */
    boolean updateSysUserById(SysUserUpdateParam sysUser);

    /**
     * 通过id删除SysUser
     *
     * @param idParam
     * @return
     */
    boolean removeSysUserById(IdParam idParam);

    boolean removeSysUsersById(IdBatchParam idParam);

    /**
     * 通过id查询SysUser
     *
     * @param idParam
     * @return
     */
    SysUserDeptDTO getSysUserById(IdParam idParam);

    /**
     * 查询SysUserQueryVo的分页结果
     *
     * @param sysUserQueryPageParam
     * @return
     */
    IPage<SysUserDeptDTO> pageSysUserQueryVo(SysUserQueryPageParam sysUserQueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
    boolean resetPasswd(IdBatchParam idBatchParam);

    List<SysUserRoleDTO> getUserRolesByUserId(IdParam idParam);
}
