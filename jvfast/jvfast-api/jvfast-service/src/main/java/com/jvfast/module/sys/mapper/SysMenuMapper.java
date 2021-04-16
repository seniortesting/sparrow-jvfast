package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysMenu;
import com.jvfast.module.sys.model.param.SysMenuQueryPageParam;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * <p>
     * sys_menu分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysMenuQueryVo> pageSysMenu(IPage page, @Param("query") SysMenuQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    /**
     * 查询菜单通过角色标识
     *
     * @param roleCodes
     * @return java.util.Set<com.jvfast.module.sys.model.vo.SysMenuQueryVo>
     * @author Walter Hu
     * @date 2019/12/24
     * @since 1.8
     */
    Set<SysMenuQueryVo> listPermissionByRoleCodes(@Param("roleCodes") Set<String> roleCodes);

    List<SysMenuQueryVo> listRouterByUserId(Long userId);

    List<SysMenuQueryVo> listTopMenuByUserId(Long userId);

    /**
     * 查询菜单通过角色ID
     *
     * @param roleId
     * @return java.util.Set<com.jvfast.module.sys.model.vo.SysMenuQueryVo>
     * @author Walter Hu
     * @date 2019/12/24
     * @since 1.8
     */
    List<SysMenuQueryVo> listMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 顶部菜单对应的子菜单,用于删除/更新递归
     *
     * @param id
     * @return java.util.List<com.jvfast.module.sys.model.vo.SysMenuQueryVo>
     * @author Walter Hu
     * @date 2019/12/25
     * @since 1.8
     */
    List<SysMenuQueryVo> listMenuChildrenByMenuId(@Param("id") Long id);
}
