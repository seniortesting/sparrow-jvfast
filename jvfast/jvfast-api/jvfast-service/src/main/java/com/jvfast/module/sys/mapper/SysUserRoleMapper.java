package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jvfast.module.sys.model.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-20
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /***************************** 以下为扩展接口 ******************************************************/
}
