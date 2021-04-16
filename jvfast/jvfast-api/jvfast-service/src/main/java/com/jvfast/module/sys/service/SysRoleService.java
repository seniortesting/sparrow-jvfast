package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysRole;
import com.jvfast.module.sys.model.param.SysRoleAddParam;
import com.jvfast.module.sys.model.param.SysRoleQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleQueryVo;
import org.apache.shiro.dao.DataAccessException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 添加AuthRole
     *
     * @param authRole
     * @return
     */
    boolean saveSysRole(SysRoleAddParam authRole);

    /**
     * 通过id更新AuthRole
     *
     * @param authRole
     * @return
     */
    boolean updateSysRoleById(SysRoleUpdateParam authRole);

    /**
     * 通过id删除AuthRole
     *
     * @param idParam
     * @return
     */
    boolean removeSysRoleById(IdParam idParam);

    boolean removeSysRolesById(IdBatchParam idParam);

    /**
     * 通过id查询AuthRole
     *
     * @param idParam
     * @return
     */
    SysRole getSysRoleById(IdParam idParam);


    /**
     * 查询AuthRoleQueryVo的所有结果
     *
     * @return
     */
    List<SysRoleQueryVo> listSysRoleQueryVo();

    /**
     * 查询AuthRoleQueryVo的分页结果
     *
     * @param authRoleQueryPageParam
     * @return
     */
    IPage<SysRoleQueryVo> pageSysRoleQueryVo(SysRoleQueryPageParam authRoleQueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
    /**
     * 通过用户id获取对应的角色列表
     *
     * @param userId
     * @return
     * @throws DataAccessException
     */
    Set<String> getRoleCodesByUserId(String userId) throws DataAccessException;
}
