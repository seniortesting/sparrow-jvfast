package com.jvfast.wx.common.entity;

import lombok.Data;

/**
 * 微信用户信息，保存到数据库中对应的表的实体类
 * @author Walter
 */
@Data
public class WxUserDetail {

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
