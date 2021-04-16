package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysRoleMenu;
import com.jvfast.module.sys.model.param.SysRoleMenuQueryPageParam;
import com.jvfast.module.sys.model.vo.SysRoleMenuQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * <p>
     * sys_role_menu分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysRoleMenuQueryVo> getSysRoleMenuPage(IPage page, @Param("query") SysRoleMenuQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
