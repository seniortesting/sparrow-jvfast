package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysUserPost;
import com.jvfast.module.sys.model.param.SysUserPostQueryPageParam;
import com.jvfast.module.sys.model.vo.SysUserPostQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 岗位表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

    /**
     * <p>
     * sys_user_post分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysUserPostQueryVo> getSysUserJobPage(IPage page, @Param("query") SysUserPostQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
