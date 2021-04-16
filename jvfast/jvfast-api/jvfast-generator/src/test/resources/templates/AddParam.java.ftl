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
 *
 * ${table.comment!}添加参数
 *
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@ApiModel(value = "${entity}AddParam对象", description = "添加${table.comment!}请求参数")
</#if>
<#if entityLombokModel>
@Data
@NoArgsConstructor
</#if>
<#if superEntityClass??>
public class ${entity}AddParam implements Serializable {
<#elseif activeRecord>
public class ${entity}AddParam extends Model<${entity}> {
<#else>
public class ${entity}AddParam implements Serializable {
</#if>
<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.propertyName=="id"||
         field.propertyName =="status"||
         field.propertyName =="version"||
         field.propertyName =="updateBy"||
    field.propertyName =="updateTime">
<#--       此处不填充数据 -->
    <#else>
    <#if field.comment??>
    /**
    * ${field.comment}
    */
    </#if>
    <#if swagger2>
    @ApiModelProperty(value = "${field.comment!}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
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
     * 对应记录的创建时间
    */
    @ApiModelProperty(value = "对应记录的创建时间")
    private LocalDateTime createTime;
    /**
     * 对应记录的创建者
    */
    @ApiModelProperty(value = "对应记录的创建者")
    private String createBy;
<#-- ----------  END 字段循环遍历  ---------->
}
