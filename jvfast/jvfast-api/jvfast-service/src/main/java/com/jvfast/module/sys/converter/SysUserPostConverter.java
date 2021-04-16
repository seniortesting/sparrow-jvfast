package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysUserPost;
import com.jvfast.module.sys.model.param.SysUserPostAddParam;
import com.jvfast.module.sys.model.param.SysUserPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserPostQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysUserPost对象转换接口
 */
@Mapper
public interface SysUserPostConverter {

    SysUserPostConverter INSTANCE = Mappers.getMapper(SysUserPostConverter.class);

    /**
     * 添加操作
     * SysUserPostAddParam对象转换为SysUserJob
     *
     * @param sysUserPostAddParam
     * @return
     */
    SysUserPost convertSysUserPostAddParam(SysUserPostAddParam sysUserPostAddParam);

    /**
     * 修改操作
     * SysUserJobUpdateParam对象转换为SysUserJob
     *
     * @param sysUserPostUpdateParam
     * @return
     */
    SysUserPost convertSysUserPostUpdateParam(SysUserPostUpdateParam sysUserPostUpdateParam);

    /**
     * 查询列表转化实体
     * SysUserJob对象转换为SysUserPostQueryVo
     *
     * @param sysUserPosts
     * @return
     */
    List<SysUserPostQueryVo> convertSysUserPostList(List<SysUserPost> sysUserPosts);

    /**
     * 查询分页操作
     * SysUserJob对象转换为SysUserPostQueryVo
     *
     * @param sysUserPost
     * @return
     */
    SysUserPostQueryVo convertSysUserPost(SysUserPost sysUserPost);


}
