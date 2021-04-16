package com.jvfast.module.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jvfast.common.constant.PageConst;
import com.jvfast.common.constant.SysConst;
import com.jvfast.common.entity.TreeManager;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.common.shiro.constant.SecurityConst;
import com.jvfast.module.sys.converter.SysMenuConverter;
import com.jvfast.module.sys.mapper.SysMenuMapper;
import com.jvfast.module.sys.model.entity.SysMenu;
import com.jvfast.module.sys.model.entity.SysRole;
import com.jvfast.module.sys.model.param.SysMenuAddParam;
import com.jvfast.module.sys.model.param.SysMenuQueryPageParam;
import com.jvfast.module.sys.model.param.SysMenuQueryTypeParam;
import com.jvfast.module.sys.model.param.SysMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import com.jvfast.module.sys.service.SysMenuService;
import com.jvfast.module.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    private final SysMenuMapper sysMenuMapper;
    private final SysRoleService sysRoleService;
    private static final Integer BUTTON_PERMISSION_MENU_TYPE = 3;

    /**
     * 添加SysMenu
     *
     * @param sysMenuAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysMenu(SysMenuAddParam sysMenuAddParam) {
        // 对象转换
        String externalUrl = sysMenuAddParam.getExternalUrl();
        if (StrUtil.isNotEmpty(externalUrl)) {
            sysMenuAddParam.setComponentName(externalUrl);
        }
        SysMenu sysMenu = SysMenuConverter.INSTANCE.convertSysMenuAddParam(sysMenuAddParam);
        boolean savedResult = save(sysMenu);
        return savedResult;
    }

    /**
     * 通过id更新SysMenu
     *
     * @param sysMenuUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysMenuById(SysMenuUpdateParam sysMenuUpdateParam) {
        //对象转换
        String externalUrl = sysMenuUpdateParam.getExternalUrl();
        if (StrUtil.isNotEmpty(externalUrl)) {
            sysMenuUpdateParam.setComponentName(externalUrl);
        }
        SysMenu sysMenu = SysMenuConverter.INSTANCE.convertSysMenuUpdateParam(sysMenuUpdateParam);
        boolean updatedResult = updateById(sysMenu);
        // 如果设置隐藏或者不可用,需要设置所有的子节点都不可用
        Long id = sysMenuUpdateParam.getId();
        Boolean hidden = sysMenuUpdateParam.getHidden();
        Boolean enabled = sysMenuUpdateParam.getStatus();
        List<SysMenuQueryVo> sysMenuQueryVos = sysMenuMapper.listMenuChildrenByMenuId(id);
        List<SysMenu> sysMenus = SysMenuConverter.INSTANCE.convertSysMenuList(sysMenuQueryVos);
        List<SysMenu> sysMenuStream = sysMenus.stream().map(m -> {
            m.setHidden(hidden);
            m.setStatus(enabled);
            return m;
        }).collect(Collectors.toList());
        updatedResult = updateBatchById(sysMenuStream);
        return updatedResult;
    }

    /**
     * 通过id删除SysMenu
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysMenuById(IdParam idParam) {
        Long id = idParam.getId();
        // 查询所有的节点及其子节点
        List<SysMenuQueryVo> sysMenuQueryVos = sysMenuMapper.listMenuChildrenByMenuId(id);
        List<Long> menuIds = sysMenuQueryVos.stream().map(menu -> menu.getId()).collect(Collectors.toList());
        boolean removedResult = removeByIds(menuIds);
        return removedResult;
    }

    @Override
    public boolean removeSysMenusById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysMenu
     *
     * @param idParam
     * @return
     */
    @Override
    public SysMenu getSysMenuById(IdParam idParam) {
        Long id = idParam.getId();
        SysMenu getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysMenuQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysMenuQueryVo> listSysMenuQueryVo(SysMenuQueryTypeParam sysMenuQueryTypeParam) {
        LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getStatus, 1);
        if (sysMenuQueryTypeParam.getFilterButtonPermission()) {
            sysMenuLambdaQueryWrapper.ne(SysMenu::getMenuType, BUTTON_PERMISSION_MENU_TYPE);
        }
        sysMenuLambdaQueryWrapper.orderByAsc(SysMenu::getPid, SysMenu::getMenuOrder);
        List<SysMenu> listsysMenu = list(sysMenuLambdaQueryWrapper);
        List<SysMenuQueryVo> listSysMenuQueryVo = SysMenuConverter.INSTANCE.convertSysMenuQueryList(listsysMenu);
        listSysMenuQueryVo = TreeManager.merge(listSysMenuQueryVo, SysConst.DEFAULT_PARENT_ID);
        return listSysMenuQueryVo;
    }

    /**
     * 查询SysMenuQueryVo的分页结果
     *
     * @param sysMenuQueryPageParam
     * @return
     */
    @Override
    public IPage<SysMenuQueryVo> pageSysMenuQueryVo(SysMenuQueryPageParam sysMenuQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysMenuQueryPageParam.getPageNo();
        Integer pageSize = PageConst.MAX_PAGE_SIZE;
        String keyword = sysMenuQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysMenuQueryVo> menuTree = sysMenuMapper.pageSysMenu(pagingData, sysMenuQueryPageParam);
        menuTree = TreeManager.merge(menuTree, SysConst.DEFAULT_PARENT_ID);
        pagingData.setRecords(menuTree);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public Set<SysMenuQueryVo> listPermissionByRoleCodes(Set<String> roles) {
        return sysMenuMapper.listPermissionByRoleCodes(roles);
    }

    /**
     * 每次加载页面时, 获取当前用户的所有路由
     **/
    @Override
    public List<SysMenuQueryVo> listRouterByUserId(Long userId) {
        List<SysMenuQueryVo> menus = sysMenuMapper.listRouterByUserId(userId);
        return menus;
    }

    /**
     * 前台控制,不再使用
     **/
    @Override
    public List<SysMenuQueryVo> listTopMenuByUserId(Long userId) {
        List<SysMenuQueryVo> menus = sysMenuMapper.listTopMenuByUserId(userId);
        return menus;
    }

    /**
     * 角色编辑/查看对话框
     **/
    @Override
    public List<SysMenuQueryVo> listMenusByRoleId(Long roleId) {
        List<SysMenuQueryVo> menuList = Lists.newArrayList();
        SysRole sysRole = sysRoleService.getById(roleId);
        if (Objects.nonNull(sysRole)) {
            // 是否是管理员,如果是,则返回所有的菜单
            String roleCode = sysRole.getRoleCode();
            if (SecurityConst.ADMIN_ROLE.equalsIgnoreCase(roleCode)) {
                menuList = sysMenuMapper.listMenuByRoleId(null);
            } else {
                menuList = sysMenuMapper.listMenuByRoleId(roleId);
            }

        }
        return menuList;
    }

    /**
     * @param idParam
     * @return java.util.List<com.jvfast.module.sys.model.vo.SysMenuQueryVo>
     * @author Walter Hu
     * @date 2020/1/10
     * @since 1.8
     * @deprecated 不再使用, 采用前端递归查询
     */
    @Override
    public List<SysMenuQueryVo> listMenuTreeByMenuId(IdParam idParam) {
        Long menuId = idParam.getId();
        List<SysMenuQueryVo> sysMenuQueryVos = sysMenuMapper.listMenuChildrenByMenuId(menuId);
        // 将对应的数据转换为树形结构
        if (!sysMenuQueryVos.isEmpty()) {
            sysMenuQueryVos = TreeManager.merge(sysMenuQueryVos, menuId);
        }
        return sysMenuQueryVos;
    }
}
