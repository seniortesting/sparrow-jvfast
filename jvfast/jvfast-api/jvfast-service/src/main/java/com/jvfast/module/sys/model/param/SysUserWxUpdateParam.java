package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 微信用户
 * </p>
 * @author Walter Hu
 * @since 2020-02-03
 */
@Data
@NoArgsConstructor
@ApiModel(value = "SysUserWxUpdateParam对象", description = "修改微信用户请求参数")
public class SysUserWxUpdateParam implements Serializable {
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
