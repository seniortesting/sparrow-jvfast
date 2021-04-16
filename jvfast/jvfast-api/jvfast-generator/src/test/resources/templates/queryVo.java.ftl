package ${cfg.queryVoPackage};

<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
</#if>
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * ${table.comment!}查询对象
 * </p>
 * @author ${author}
 * @date ${date}
 */
<#if swagger2>
@ApiModel(value = "${entity}QueryVo对象", description = "查询${table.comment!}返回结果对象")
</#if>
<#if entityLombokModel>
@Data
</#if>
public class ${entity}QueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ------------>
<#--普通字段-->
<#list table.fields as field>
    <#if field.comment??>
    /**
     * ${field.comment}
    */
    </#if>
    <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#--通用字段-->
<#list table.commonFields as field>
    <#if field.comment??>
    /**
     * ${field.comment}
    */
    </#if>
    <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

}
