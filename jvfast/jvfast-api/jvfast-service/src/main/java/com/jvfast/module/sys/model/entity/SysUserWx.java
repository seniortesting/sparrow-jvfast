package com.jvfast.module.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jvfast.common.config.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 微信用户
 * </p>
 * @author Walter Hu
 * @since 2020-02-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_wx")
@ApiModel(value = "SysUserWx对象", description = "微信用户")
public class SysUserWx extends BaseEntity {
    private static final long serialVersionUID=1L;
   /**
    * 记录唯一标识id
    */
   @ApiModelProperty(value = "记录唯一标识id")
   @TableId(value = "id", type = IdType.ASSIGN_ID)
   private Long id;
   private String appId;
   private String userId;
   private Boolean subscribe;
   private String openId;
   private String nickname;
   @TableField("sexDesc")
   private String sexDesc;
   private String sex;
   private String lang;
   private String city;
   private String province;
   private String country;
   @TableField("headImgUrl")
   private String headImgUrl;
   @TableField("subscribeTime")
   private String subscribeTime;
   @TableField("subscribeScene")
   private String subscribeScene;
   @TableField("unionId")
   private String unionId;


}
