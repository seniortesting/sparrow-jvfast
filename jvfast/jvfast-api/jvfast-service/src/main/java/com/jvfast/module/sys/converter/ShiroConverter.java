package com.jvfast.module.sys.converter;

import com.jvfast.common.shiro.model.RedisLoginSysUserVo;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.param.SysUserRegisterParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * AuthRole对象转换接口
 */
@Mapper
public interface ShiroConverter {

    ShiroConverter INSTANCE = Mappers.getMapper(ShiroConverter.class);

    /**
     * 将数据库中用户转为redis存储的用户
     *
     * @param sysUser
     * @return
     */
    RedisLoginSysUserVo convertSysUserToRedisSysUser(SysUser sysUser);

    /**
     * 将redis缓存中对应的用户信息转为实际用户信息
     *
     * @param redisLoginSysUserVo
     * @return com.jvfast.module.sys.model.entity.SysUser
     * @author Walter Hu
     * @date 2020/1/2
     * @since 1.8
     */
    SysUser convertRedisSysUserToSysUser(RedisLoginSysUserVo redisLoginSysUserVo);

    /**
     * 将注册用户信息转为保存数据库用户信息
     *
     * @param sysUserRegisterParam
     * @return
     */
    @Mappings({
            @Mapping(source = "username", target = "userName")
    })
    SysUser convertRegisterParamToSysUser(SysUserRegisterParam sysUserRegisterParam);

}
