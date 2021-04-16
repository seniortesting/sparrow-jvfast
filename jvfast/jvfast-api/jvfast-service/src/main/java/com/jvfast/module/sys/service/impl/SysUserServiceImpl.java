package com.jvfast.module.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.entity.TreeManager;
import com.jvfast.common.exception.DaoExistException;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.common.shiro.model.RedisLoginSysUserVo;
import com.jvfast.common.shiro.service.JWTRedisService;
import com.jvfast.common.util.EncryptUtil;
import com.jvfast.common.util.RegUtil;
import com.jvfast.module.sys.constant.ConfigConst;
import com.jvfast.module.sys.converter.ShiroConverter;
import com.jvfast.module.sys.converter.SysUserConverter;
import com.jvfast.module.sys.mapper.SysDeptMapper;
import com.jvfast.module.sys.mapper.SysUserMapper;
import com.jvfast.module.sys.model.dto.SysUserDeptDTO;
import com.jvfast.module.sys.model.dto.SysUserRoleDTO;
import com.jvfast.module.sys.model.entity.SysConfig;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.entity.SysUserRole;
import com.jvfast.module.sys.model.param.SysUserAddParam;
import com.jvfast.module.sys.model.param.SysUserQueryPageParam;
import com.jvfast.module.sys.model.param.SysUserUpdateParam;
import com.jvfast.module.sys.model.vo.SysDeptQueryVo;
import com.jvfast.module.sys.service.SysConfigService;
import com.jvfast.module.sys.service.SysUserDeptService;
import com.jvfast.module.sys.service.SysUserRoleService;
import com.jvfast.module.sys.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统用户信息表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    private final SysUserMapper sysUserMapper;
    private final JWTRedisService JWTRedisService;

    private final SysUserRoleService sysUserRoleService;
    private final SysUserDeptService sysUserDeptService;

    private final SysDeptMapper sysDeptMapper;

    private final SysConfigService sysConfigService;


    @Override
    public boolean addSysUser(SysUserAddParam sysUserAddParam) {
        // 是否账号已经注册过
        String userName = sysUserAddParam.getUserName();
        String email = sysUserAddParam.getEmail();
        String phone = sysUserAddParam.getPhone();
        String passwd = sysUserAddParam.getPasswd();
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getId)
                .eq(SysUser::getUserName, userName);
        if (RegUtil.match(RegUtil.EMAIL, email)) {
            sysUserLambdaQueryWrapper.or().eq(SysUser::getEmail, email);
        }
        if (RegUtil.match(RegUtil.CHINA_PHONE, phone)) {
            sysUserLambdaQueryWrapper.or().eq(SysUser::getPhone, phone);
        }

        SysUser foundSysUser = getOne(sysUserLambdaQueryWrapper, false);
        if (foundSysUser != null) {
            String errMsg = "用户已经注册过,不能重复注册";
            log.warn(errMsg);
            throw new DaoExistException(errMsg);
        }
        if (StrUtil.isEmpty(passwd)) {
            passwd = getDefaultMD5PasswordConfigValue();
        }
        if (StrUtil.isNotEmpty(passwd)) {
            String encryptPasswd = EncryptUtil.encryptPasswd(passwd);
            sysUserAddParam.setPasswd(encryptPasswd);
        }
        SysUser sysUser = SysUserConverter.INSTANCE.convertSysUserAddParam(sysUserAddParam);
        boolean savedUser = save(sysUser);
        // 修改对应的用户角色和用户部门信息
        if (savedUser) {
            Long userId = sysUser.getId();
            Long deptId = sysUserAddParam.getDeptId();
            List<String> roleIds = sysUserAddParam.getRoleId();
            savedUser = updateUserRoleAndDept(userId, roleIds, deptId);
        }
        return savedUser;
    }

    /**
     * 通过id更新SysUser
     *
     * @param sysUserUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUserById(SysUserUpdateParam sysUserUpdateParam) {
        //对象转换
        Long userId = sysUserUpdateParam.getId();
        String passwd = sysUserUpdateParam.getPasswd();
        List<String> roleIds = sysUserUpdateParam.getRoleId();
        Long deptId = sysUserUpdateParam.getDeptId();
        if (StrUtil.isNotEmpty(passwd)) {
            String encryptPasswd = EncryptUtil.encryptPasswd(passwd);
            sysUserUpdateParam.setPasswd(encryptPasswd);
        }
        SysUser sysUser = SysUserConverter.INSTANCE.convertSysUserUpdateParam(sysUserUpdateParam);
        boolean updatedResult = updateById(sysUser);
        // 是否需要更新角色信息
        if (updatedResult) {
            updatedResult = updateUserRoleAndDept(userId, roleIds, deptId);
        }
        // 更新redis缓存信息,针对个人资料更新
        String token = sysUserUpdateParam.getToken();
        if (updatedResult && token != null) {
            RedisLoginSysUserVo newRedisLoginSysUser = ShiroConverter.INSTANCE.convertSysUserToRedisSysUser(sysUser);
            RedisLoginSysUserVo loginSysUserRedisVo = JWTRedisService.getLoginSysUserRedisVo(token);
            if (loginSysUserRedisVo != null) {
                newRedisLoginSysUser.setPermissions(loginSysUserRedisVo.getPermissions());
                newRedisLoginSysUser.setRoles(loginSysUserRedisVo.getRoles());
                // 保存新的信息
                JWTRedisService.cacheLoginInfo(token, newRedisLoginSysUser);
            }
        }
        return updatedResult;
    }

    /**
     * 通过id删除SysUser
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysUserById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        if (removedResult) {
            removeUserRoleAndDept(id);
        }
        return removedResult;
    }

    @Override
    public boolean removeSysUsersById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removeIds = removeByIds(ids);
        if (removeIds) {
            ids.forEach(id -> {
                removeUserRoleAndDept(id);
            });
        }
        return removeIds;
    }

    /**
     * 通过id查询SysUser
     *
     * @param idParam
     * @return
     */
    @Override
    public SysUserDeptDTO getSysUserById(IdParam idParam) {
        SysUserDeptDTO sysUserDeptDTO = null;
        List<SysUserDeptDTO> getResult = sysUserMapper.getSysUserById(idParam);
        if (getResult.size() > 0) {
            sysUserDeptDTO = getResult.get(0);
        }
        return sysUserDeptDTO;
    }

    /**
     * 查询SysUserQueryVo的分页结果
     *
     * @param sysUserQueryPageParam
     * @return
     */
    @Override
    public IPage<SysUserDeptDTO> pageSysUserQueryVo(SysUserQueryPageParam sysUserQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = sysUserQueryPageParam.getPageNo();
        Integer pageSize = sysUserQueryPageParam.getPageSize();
        String keyword = sysUserQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        Long deptId = sysUserQueryPageParam.getDeptId();
        List<Long> childIds = null;
        if (!Objects.isNull(deptId)) {
            List<SysDeptQueryVo> sysDepts = sysDeptMapper.getDeptTree();
            childIds = TreeManager.mergeId(sysDepts, deptId);
            if (log.isDebugEnabled()) {
                log.debug("childIds: {}", childIds.toString());
            }
        }
        List<SysUserDeptDTO> listSysUserQueryVo = sysUserMapper.getSysUserPage(pagingData, sysUserQueryPageParam, childIds);
        pagingData.setRecords(listSysUserQueryVo);
        return pagingData;
    }


    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public boolean resetPasswd(IdBatchParam idBatchParam) {
        boolean updateBatchById = false;
        List<Long> ids = idBatchParam.getIds();
        if (!ids.isEmpty()) {
            List<SysUser> sysUsers = ids.stream().map(id -> {
                SysUser sysUser = new SysUser();
                sysUser.setId(id);
                String md5Passwd = getDefaultMD5PasswordConfigValue();
                if (StrUtil.isNotEmpty(md5Passwd)) {
                    String encryptPasswd = EncryptUtil.encryptPasswd(md5Passwd);
                    sysUser.setPasswd(encryptPasswd);
                }
                return sysUser;
            }).collect(Collectors.toList());
            updateBatchById = updateBatchById(sysUsers);
        }
        return updateBatchById;
    }

    @Override
    public List<SysUserRoleDTO> getUserRolesByUserId(IdParam idParam) {
        List<SysUserRoleDTO> sysUserRoleByUserId = sysUserMapper.getSysUserRoleByUserId(idParam);
        return sysUserRoleByUserId;
    }

    private String getDefaultMD5PasswordConfigValue() {
        String passwd = null;
        LambdaQueryWrapper<SysConfig> configLambdaQueryWrapper = Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigKey, ConfigConst.PASSWORD_CONFIG_KEY);
        SysConfig sysConfig = sysConfigService.getOne(configLambdaQueryWrapper, false);
        if (sysConfig != null) {
            passwd = EncryptUtil.md5(sysConfig.getConfigValue());
        }
        return passwd;
    }

    private boolean updateUserRoleAndDept(Long userId, List<String> roleIds, Long deptId) {
        boolean result = true;
        if (!roleIds.isEmpty()) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, userId);
            sysUserRoleService.remove(queryWrapper);
            // 进行添加对应的记录
            List<SysUserRole> authUserRoles = roleIds.stream().map(role -> {
                SysUserRole authUserRole = new SysUserRole();
                authUserRole.setUserId(userId);
                authUserRole.setRoleId(Long.parseLong(role));
                return authUserRole;
            }).collect(Collectors.toList());
            result = sysUserRoleService.saveBatch(authUserRoles);
        }
        // 是否需要修改部门Id
        if (!Objects.isNull(deptId)) {
            SysUserDept sysUserDept = new SysUserDept();
            sysUserDept.setUserId(userId);
            sysUserDept.setDeptId(deptId);
            LambdaQueryWrapper<SysUserDept> lambdaQueryWrapper = Wrappers.<SysUserDept>lambdaQuery()
                    .eq(SysUserDept::getUserId, userId);
            sysUserDeptService.remove(lambdaQueryWrapper);
            sysUserDeptService.saveOrUpdate(sysUserDept, lambdaQueryWrapper);
        }
        return result;
    }

    private boolean removeUserRoleAndDept(Long userId) {
        boolean removeResult;
        LambdaQueryWrapper<SysUserDept> sysUserDeptLambdaQueryWrapper = Wrappers.<SysUserDept>lambdaQuery()
                .select(SysUserDept::getId, SysUserDept::getUserId, SysUserDept::getDeptId)
                .eq(SysUserDept::getUserId, userId);
        LambdaQueryWrapper<SysUserRole> sysUserRoleLambdaQueryWrapper = Wrappers.<SysUserRole>lambdaQuery()
                .select(SysUserRole::getId, SysUserRole::getUserId, SysUserRole::getRoleId)
                .eq(SysUserRole::getUserId, userId);

        sysUserDeptService.remove(sysUserDeptLambdaQueryWrapper);
        removeResult = sysUserRoleService.remove(sysUserRoleLambdaQueryWrapper);
        return removeResult;
    }
}
