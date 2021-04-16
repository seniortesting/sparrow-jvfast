package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.model.param.SysUserWxQueryPageParam;
import com.jvfast.module.sys.model.vo.SysUserWxQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 * 微信用户 Mapper 接口
 * <p>
 *
 * @author Walter Hu
 */
@Mapper
public interface SysUserWxMapper extends BaseMapper<SysUserWx> {

    /**
     * sys_user_wx分页查询结果
     *
     * @author Walter Hu
     */
    List<SysUserWxQueryVo> pageSysUserWx(IPage page, @Param("query") SysUserWxQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
