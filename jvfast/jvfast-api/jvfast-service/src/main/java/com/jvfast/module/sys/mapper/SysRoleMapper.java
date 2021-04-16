package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysRole;
import com.jvfast.module.sys.model.entity.SysUserRole;
import com.jvfast.module.sys.model.param.SysRoleQueryPageParam;
import com.jvfast.module.sys.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.dao.DataAccessException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * <p>
     * auth_role分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-18
     */
    List<SysRoleQueryVo> getAuthRolePage(IPage page, @Param("query") SysRoleQueryPageParam queryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
    /**
     * 根据用户id获取对应的所有角色信息,通过,分割所有的角色
     *
     * @param userId
     * @return
     * @throws DataAccessException
     */
    Set<String> getRoleCodesByUserId(String userId) throws DataAccessException;

    /**
     * 判断当前角色是否存在用户,如果用,删除角色的时候提醒
     *
     * @param roleId
     * @return
     * @throws DataAccessException
     */
    List<SysUserRole> isExistsUserByRoleId(Long roleId) throws DataAccessException;
}
