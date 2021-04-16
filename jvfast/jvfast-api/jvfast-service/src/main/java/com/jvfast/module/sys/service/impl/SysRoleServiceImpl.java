package com.jvfast.module.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jvfast.common.exception.DaoExistException;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.common.shiro.constant.SecurityConst;
import com.jvfast.module.sys.converter.SysRoleConverter;
import com.jvfast.module.sys.mapper.SysRoleMapper;
import com.jvfast.module.sys.model.entity.SysRole;
import com.jvfast.module.sys.model.entity.SysRoleMenu;
import com.jvfast.module.sys.model.param.SysRoleAddParam;
import com.jvfast.module.sys.model.param.SysRoleQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleQueryVo;
import com.jvfast.module.sys.service.SysRoleMenuService;
import com.jvfast.module.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 添加角色
     *
     * @param authRoleAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRole(SysRoleAddParam authRoleAddParam) {
        String roleName = authRoleAddParam.getRoleName();
        String roleCode = authRoleAddParam.getRoleCode();
        List<Long> dataMenu = authRoleAddParam.getDataMenu();
        LambdaQueryWrapper<SysRole> roleLambdaQueryWrapper = Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, roleName).or()
                .eq(SysRole::getRoleCode, roleCode);
        List<SysRole> sysRoles = list(roleLambdaQueryWrapper);
        if (!sysRoles.isEmpty()) {
            // 存在对应的角色
            throw new DaoExistException("角色名称或者标识重复");
        }
        // 对象转换
        SysRole authRole = SysRoleConverter.INSTANCE.convertAuthRoleAddParam(authRoleAddParam);
        boolean savedResult = save(authRole);
        // 添加对应的菜单相关的权限
        if (savedResult) {
            Long roleId = authRole.getId();
            savedResult = insertRoleMenu(roleId, dataMenu);
        }
        return savedResult;
    }

    /**
     * 通过id更新AuthRole
     *
     * @param authRoleUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysRoleById(SysRoleUpdateParam authRoleUpdateParam) {
        //对象转换
        String roleCode = authRoleUpdateParam.getRoleCode();
        if (StrUtil.isNotEmpty(roleCode) && SecurityConst.ADMIN_ROLE.equalsIgnoreCase(roleCode)) {
            throw new AuthenticationException("没有权限修改当前角色");
        }
        List<Long> dataMenu = authRoleUpdateParam.getDataMenu();
        SysRole authRole = SysRoleConverter.INSTANCE.convertAuthRoleUpdateParam(authRoleUpdateParam);
        boolean updatedResult = updateById(authRole);
        if (updatedResult) {
            // 删除记录然后重新设置对应的权限
            Long roleId = authRole.getId();
            deleteRoleMenu(roleId);
            updatedResult = insertRoleMenu(roleId, dataMenu);
        }
        
        return updatedResult;
    }

    /**
     * 通过id删除AuthRole
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysRoleById(IdParam idParam) {
        boolean removedResult = false;
        Long id = idParam.getId();
        SysRole sysRole = getById(id);
        if (Objects.nonNull(sysRole)) {
            String roleCode = sysRole.getRoleCode();
            if (SecurityConst.ADMIN_ROLE.equalsIgnoreCase(roleCode)) {
                throw new AuthenticationException("没有权限删除该角色");
            }
            removedResult = removeById(id);
            if (removedResult) {
                deleteRoleMenu(id);
            }
        }
        return removedResult;
    }

    @Override
    public boolean removeSysRolesById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询AuthRole
     *
     * @param idParam
     * @return
     */
    @Override
    public SysRole getSysRoleById(IdParam idParam) {
        Long id = idParam.getId();
        SysRole getResult = getById(id);
        return getResult;
    }

    /**
     * 查询AuthRoleQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysRoleQueryVo> listSysRoleQueryVo() {
        // 获取启用的角色
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = Wrappers.<SysRole>lambdaQuery()
                .select(SysRole::getId, SysRole::getRoleCode, SysRole::getRoleName, SysRole::getDataScope, SysRole::getRemark, SysRole::getStatus, SysRole::getVersion)
                .eq(SysRole::getStatus, true);
        List<SysRole> listauthRole = list(lambdaQueryWrapper);
        List<SysRoleQueryVo> listAuthRoleQueryVo = SysRoleConverter.INSTANCE.convertAuthRoleList(listauthRole);
        return listAuthRoleQueryVo;
    }

    /**
     * 查询AuthRoleQueryVo的分页结果
     *
     * @param authRoleQueryPageParam
     * @return
     */
    @Override
    public IPage<SysRoleQueryVo> pageSysRoleQueryVo(SysRoleQueryPageParam authRoleQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = authRoleQueryPageParam.getPageNo();
        Integer pageSize = authRoleQueryPageParam.getPageSize();
        String keyword = authRoleQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysRoleQueryVo> listAuthRoleQueryVo = sysRoleMapper.getAuthRolePage(pagingData, authRoleQueryPageParam);
        pagingData.setRecords(listAuthRoleQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public Set<String> getRoleCodesByUserId(String userId) throws DataAccessException {
        return sysRoleMapper.getRoleCodesByUserId(userId);
    }

    private boolean insertRoleMenu(Long roleId, List<Long> menuId) {
        boolean saveBatch;
        if (!menuId.isEmpty()) {
            List<SysRoleMenu> sysRoleMenus = Lists.newArrayList();
            menuId.forEach(menu -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menu);
                sysRoleMenus.add(sysRoleMenu);
            });
            saveBatch = sysRoleMenuService.saveBatch(sysRoleMenus);
        } else {
            saveBatch = true;
        }
        return saveBatch;
    }

    private boolean deleteRoleMenu(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId);
        return sysRoleMenuService.remove(sysRoleMenuLambdaQueryWrapper);
    }
}
