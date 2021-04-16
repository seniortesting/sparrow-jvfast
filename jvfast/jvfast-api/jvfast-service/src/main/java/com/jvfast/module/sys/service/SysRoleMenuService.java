package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysRoleMenu;
import com.jvfast.module.sys.model.param.SysRoleMenuAddParam;
import com.jvfast.module.sys.model.param.SysRoleMenuQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleMenuQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 添加SysRoleMenu
     *
     * @param sysRoleMenu
     * @return
     */
    boolean saveSysRoleMenu(SysRoleMenuAddParam sysRoleMenu);

    /**
     * 通过id更新SysRoleMenu
     *
     * @param sysRoleMenu
     * @return
     */
    boolean updateSysRoleMenuById(SysRoleMenuUpdateParam sysRoleMenu);

    /**
     * 通过id删除SysRoleMenu
     *
     * @param idParam
     * @return
     */
    boolean removeSysRoleMenuById(IdParam idParam);

    /**
     * 通过id查询SysRoleMenu
     *
     * @param idParam
     * @return
     */
    SysRoleMenu getSysRoleMenuById(IdParam idParam);


    /**
     * 查询SysRoleMenuQueryVo的所有结果
     *
     * @return
     */
    List<SysRoleMenuQueryVo> listSysRoleMenuQueryVo();

    /**
     * 查询SysRoleMenuQueryVo的分页结果
     *
     * @param sysRoleMenuQueryPageParam
     * @return
     */
    IPage<SysRoleMenuQueryVo> pageSysRoleMenuQueryVo(SysRoleMenuQueryPageParam sysRoleMenuQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
