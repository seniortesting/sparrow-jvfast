package com.jvfast.module.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md
 * </p>
 * @author Walter Hu
 * @since 2020-02-07
 */
@Data
@NoArgsConstructor
@TableName("sys_city")
@ApiModel(value = "SysCity对象", description = "国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md")
public class SysCity {
    private static final long serialVersionUID=1L;
   /**
    * https://download.csdn.net/download/inlove2009/10620858
    */
   @ApiModelProperty(value = "https://download.csdn.net/download/inlove2009/10620858")
   @TableId(value = "id", type = IdType.ASSIGN_ID)
   private Long id;
   /**
    * 上级ID
    */
   @ApiModelProperty(value = "上级ID")
   private Long pid;
   /**
    * 层级深度，0：省，1：市，2：区，3：镇
    */
   @ApiModelProperty(value = "层级深度，0：省，1：市，2：区，3：镇")
   private Long level;
   private Double lat;
   private Double lng;
   /**
    * 统计局原始的编号；如果是添加的港澳台等数据，此编号为0
    */
   @ApiModelProperty(value = "统计局原始的编号；如果是添加的港澳台等数据，此编号为0")
   private String areaCode;
   private String cityCode;
   /**
    * 城市名称，为统计局的名称精简过后的
    */
   @ApiModelProperty(value = "城市名称，为统计局的名称精简过后的")
   private String name;
   /**
    * name的拼音前缀，取的是pinyin第一个字母；用来排序时应当先根据拼音前缀排序，相同的再根据名称排序
    */
   @ApiModelProperty(value = "name的拼音前缀，取的是pinyin第一个字母；用来排序时应当先根据拼音前缀排序，相同的再根据名称排序")
   private String pinyinPrefix;
   /**
    * name的完整拼音
    */
   @ApiModelProperty(value = "name的完整拼音")
   private String pinyin;
   private LocalDateTime createTime;
   private LocalDateTime updateTime;
   private String updateBy;
   private String createBy;

}
