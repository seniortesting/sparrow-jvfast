package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 微信用户 查询结果对象
 * </p>
 * @author Walter Hu
 * @date 2020-02-03
 */
@Data
@ApiModel(value = "SysUserWxQueryVo对象", description = "查询微信用户返回结果对象")
public class SysUserWxQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;
    private String appId;
    private String userId;
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
