package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysMenu;
import com.jvfast.module.sys.model.param.SysMenuAddParam;
import com.jvfast.module.sys.model.param.SysMenuQueryPageParam;
import com.jvfast.module.sys.model.param.SysMenuQueryTypeParam;
import com.jvfast.module.sys.model.param.SysMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 添加SysMenu
     *
     * @param sysMenu
     * @return
     */
    boolean saveSysMenu(SysMenuAddParam sysMenu);

    /**
     * 通过id更新SysMenu
     *
     * @param sysMenu
     * @return
     */
    boolean updateSysMenuById(SysMenuUpdateParam sysMenu);

    /**
     * 通过id删除SysMenu
     *
     * @param idParam
     * @return
     */
    boolean removeSysMenuById(IdParam idParam);

    boolean removeSysMenusById(IdBatchParam idParam);

    /**
     * 通过id查询SysMenu
     *
     * @param idParam
     * @return
     */
    SysMenu getSysMenuById(IdParam idParam);


    /**
     * 查询SysMenuQueryVo的所有结果
     *
     * @return
     */
    List<SysMenuQueryVo> listSysMenuQueryVo(SysMenuQueryTypeParam sysMenuQueryTypeParam);

    /**
     * 查询SysMenuQueryVo的分页结果
     *
     * @param sysMenuQueryPageParam
     * @return
     */
    IPage<SysMenuQueryVo> pageSysMenuQueryVo(SysMenuQueryPageParam sysMenuQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/

    /**
     * 查询侧边菜单通过用户角色
     *
     * @param roleCodes
     * @return java.util.Set<com.jvfast.module.sys.model.vo.SysMenuQueryVo>
     * @author Walter Hu
     * @date 2019/12/24
     * @since 1.8
     */
    Set<SysMenuQueryVo> listPermissionByRoleCodes(Set<String> roleCodes);

    List<SysMenuQueryVo> listRouterByUserId(Long userId);

    List<SysMenuQueryVo> listTopMenuByUserId(Long userId);

    /*
     * 查询侧边菜单通过对应的角色id
     */
    List<SysMenuQueryVo> listMenusByRoleId(Long roleId);

    List<SysMenuQueryVo> listMenuTreeByMenuId(IdParam idParam);

}
