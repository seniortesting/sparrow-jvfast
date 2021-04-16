package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysPost;
import com.jvfast.module.sys.model.param.SysPostAddParam;
import com.jvfast.module.sys.model.param.SysPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysPostQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysPost对象转换接口
 */
@Mapper
public interface SysPostConverter {

    SysPostConverter INSTANCE = Mappers.getMapper(SysPostConverter.class);

    /**
     * 添加操作
     * SysJobAddParam对象转换为SysJob
     *
     * @param sysPostAddParam
     * @return
     */
    SysPost convertSysPostAddParam(SysPostAddParam sysPostAddParam);

    /**
     * 修改操作
     * SysJobUpdateParam对象转换为SysJob
     *
     * @param sysPostUpdateParam
     * @return
     */
    SysPost convertSysPostUpdateParam(SysPostUpdateParam sysPostUpdateParam);

    /**
     * 查询列表转化实体
     * SysJob对象转换为SysJobQueryVo
     *
     * @param sysPost
     * @return
     */
    List<SysPostQueryVo> convertSysPostList(List<SysPost> sysPost);

    /**
     * 查询分页操作
     * SysJob对象转换为SysJobQueryVo
     *
     * @param sysPost
     * @return
     */
    SysPostQueryVo convertSysPost(SysPost sysPost);


}
