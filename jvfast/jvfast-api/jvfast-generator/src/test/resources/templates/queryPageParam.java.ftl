package ${cfg.queryParamPackage};

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ${cfg.queryParamPath};

/**
 * ${table.comment!}分页查询参数
 *
 * @author ${author}
 * @date ${date}
 */
<#if swagger2>
@ApiModel(value = "${entity}QueryPageParam分页查询对象", description = "查询${table.comment!}分页请求参数")
</#if>
@Data
@NoArgsConstructor
<#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
</#if>
<#if cfg.pageListOrder>
public class ${entity}QueryPageParam extends QueryOrderParam {
<#else>
public class ${entity}QueryPageParam extends QueryParam {
</#if>
    private static final long serialVersionUID = 1L;
    /**
     * 其它查询条件
    */
}
