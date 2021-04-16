package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.module.sys.mapper.SysUserRoleMapper;
import com.jvfast.module.sys.model.entity.SysUserRole;
import com.jvfast.module.sys.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-20
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /***************************** 以下为扩展接口 ******************************************************/
}
