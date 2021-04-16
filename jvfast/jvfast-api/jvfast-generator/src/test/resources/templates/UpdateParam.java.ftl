package ${cfg.queryParamPackage};

<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
</#if>

/**
 * <p>
 * ${table.comment!}修改更新参数
 * </p>
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@ApiModel(value = "${entity}UpdateParam对象", description = "修改${table.comment!}请求参数")
</#if>
<#if entityLombokModel>
@Data
@NoArgsConstructor
</#if>
<#if superEntityClass??>
public class ${entity}UpdateParam implements Serializable {
<#elseif activeRecord>
public class ${entity}UpdateParam extends Model<${entity}> {
<#else>
public class ${entity}UpdateParam implements Serializable {
</#if>
<#if entitySerialVersionUID>
   private static final long serialVersionUID = 1L;
</#if>
<#--## ----------  BEGIN 字段循环遍历  ------------>
<#list table.fields as field>
    <#if field.propertyName== "createTime" ||field.propertyName =="createBy" >
<#--        nothig done-->
    <#else>
        <#if field.comment??>
   /**
   * ${field.comment}
   */
        </#if>
        <#if swagger2>
   @ApiModelProperty(value = "${field.comment}")
        </#if>
    </#if>
   private ${field.propertyType} ${field.propertyName};
</#list>
   /**
    * 对应记录是否可用，1可用，0不可用
   */
   @ApiModelProperty(value = "对应记录是否可用，1可用，0不可用")
   private Boolean status;
   /**
    * 对应记录备注
   */
   @ApiModelProperty(value = "对应记录备注")
   private String remark;
   /**
    * 对应记录的修订版本号,乐观锁
   */
   @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
   private Integer version;
   /**
    * 对应记录的最后修改时间
   */
   @ApiModelProperty(value = "对应记录的最后修改时间")
   private LocalDateTime updateTime;
   /**
    * 对应记录的最后修改者
   */
   @ApiModelProperty(value = "对应记录的最后修改者")
   private String updateBy;
<#--## ----------  END 字段循环遍历  ------------>
}
