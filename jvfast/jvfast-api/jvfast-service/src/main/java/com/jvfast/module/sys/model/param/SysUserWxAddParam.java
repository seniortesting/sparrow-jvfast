package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 微信用户
 *
 * @author Walter Hu
 * @since 2020-02-03
 */
@Data
@NoArgsConstructor
@ApiModel(value = "SysUserWxAddParam对象", description = "添加微信用户请求参数")
public class SysUserWxAddParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String appId;

    private String userId;

    private Boolean subscribe;

    private String openId;

    private String nickname;

    private String sexDesc;

    private String sex;

    private String lang;

    private String city;

    private String province;

    private String country;

    private String headImgUrl;

    private String subscribeTime;

    private String subscribeScene;

    private String unionId;
}
