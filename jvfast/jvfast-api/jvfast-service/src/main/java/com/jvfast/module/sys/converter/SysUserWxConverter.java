package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.model.param.SysUserWxAddParam;
import com.jvfast.module.sys.model.param.SysUserWxUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserWxQueryVo;
import com.jvfast.wx.common.entity.WxUserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysUserWx对象转换接口
 * @author Walter Hu
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface SysUserWxConverter {

    SysUserWxConverter INSTANCE = Mappers.getMapper(SysUserWxConverter.class);

    SysUserWx convertWxMpUser(WxUserDetail wxMpUser);
    /**
     *  添加操作
     *  <p>
     *  SysUserWxAddParam对象转换为SysUserWx
     *
     * @param sysUserWxAddParam
     * @return
     */
    SysUserWx convertSysUserWxAddParam(SysUserWxAddParam sysUserWxAddParam);

    /**
     *  修改操作
     *  <p>
     *  SysUserWxUpdateParam对象转换为SysUserWx
     *
     * @param sysUserWxUpdateParam
     * @return
     */
    SysUserWx convertSysUserWxUpdateParam(SysUserWxUpdateParam sysUserWxUpdateParam);

    /**
     *  查询列表转化实体
     *  <p>
     *  SysUserWx对象转换为SysUserWxQueryVo
     *
     * @param sysUserWx
     * @return
     */
    List<SysUserWxQueryVo> convertSysUserWxList(List<SysUserWx> sysUserWx);

    /**
     *  查询分页操作
     *  <p>
     *  SysUserWx对象转换为SysUserWxQueryVo
     *
     * @param sysUserWx
     * @return
     */
    SysUserWxQueryVo convertSysUserWx(SysUserWx sysUserWx);

}
