package com.jvfast.wx.common.converter;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.jvfast.wx.common.entity.WxUserDetail;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 存放到数据库的微信用户信息
 *
 * @author Walter
 */
@Mapper
public interface WxUserConverter {

    WxUserConverter INSTANCE = Mappers.getMapper(WxUserConverter.class);
    /**
     * 转换公众号的用户信息到数据库用户信息实体
     * @param wxMpUser
     * @return
     */
    @Mappings({
            @Mapping(source = "language",target = "lang")
    })
    WxUserDetail fromWxMpUser(WxMpUser wxMpUser);

    /**
     * 转换小程序的用户信息到数据库用户信息实体
     * @param wxMaUserInfo
     * @return
     */
    @Mappings({
            @Mapping(source = "nickName",target = "nickname"),
            @Mapping(source = "gender",target = "sex"),
            @Mapping(source = "language",target = "lang"),
            @Mapping(source = "avatarUrl",target = "headImgUrl")
    })
    WxUserDetail fromWxMaUser(WxMaUserInfo wxMaUserInfo);
}
