package ${package.Controller};

import java.util.List;

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${cfg.queryParamPackage}.${entity}QueryPageParam;
import ${cfg.queryVoPackage}.${entity}QueryVo;
import ${cfg.queryParamPackage}.${entity}AddParam;
import ${cfg.queryParamPackage}.${entity}UpdateParam;
import ${cfg.webResponsePath};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
<#if cfg.generatorStrategy>
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import ${cfg.logPackage};
import ${cfg.businessPackage};

import javax.validation.Valid;

import ${cfg.idParamPath}.IdParam;
import ${cfg.idParamPath}.IdBatchParam;
</#if>
/**
* ${table.comment!}路由接口
*
* @author ${author}
* @since ${date}
*/
<#if swagger2>
@Api(tags = "${entity}接口")
</#if>
@RequiredArgsConstructor
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#assign tableNames = table.name?split("_")>
<#if (tableNames?size>1)>
<#assign last= tableNames?size-1>
<#assign functionName=tableNames[last]?lower_case  >
<#else>
<#assign functionName= table.entityName?lower_case >
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName?lower_case}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${functionName}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
<#assign serviceName = table.serviceName?substring(0,1)?lower_case + table.serviceName?substring(1)>
<#assign entityName = entity?substring(0,1)?lower_case +entity?substring(1)>

    private final ${table.serviceName} ${serviceName};

<#if cfg.generatorStrategy>

    /**
    * 添加${table.comment!}
    */
    <#if cfg.permissionEnabled>
    @RequiresPermissions("${package.ModuleName?lower_case}:${functionName}:add")
    @Log(title = "添加${entity}", businessType = BusinessTypeEnum.ADD)
    <#else>
    @Log(title = "添加${entity}", businessType = BusinessTypeEnum.ADD)
    </#if>
    @PostMapping("/add")
    @ApiOperation(value = "添加${entity}对象", notes = "添加${table.comment!}", response = Result.class)
    public Result<Boolean> add${entity}(@Valid @RequestBody ${entity}AddParam ${entityName}AddParam) {
        boolean flag = ${serviceName}.save${entity}(${entityName}AddParam);
        return Result.status(flag);
    }

    /**
    * 修改${table.comment!}
    */
    <#if cfg.permissionEnabled>
    @RequiresPermissions("${package.ModuleName?lower_case}:${functionName}:update")
    @Log(title = "修改${entity}", businessType = BusinessTypeEnum.UPDATE)
    <#else>
    @Log(title = "修改${entity}", businessType = BusinessTypeEnum.UPDATE)
    </#if>
    @PostMapping("/update")
    @ApiOperation(value = "修改${entity}对象", notes = "修改${table.comment!}", response = Result.class)
    public Result<Boolean> update${entity}(@Valid @RequestBody ${entity}UpdateParam ${entityName}UpdateParam) {
        boolean flag = ${serviceName}.update${entity}ById(${entityName}UpdateParam);
        return Result.status(flag);
    }

    /**
    * 删除${table.comment!}
    */
    <#if cfg.permissionEnabled>
    @RequiresPermissions("${package.ModuleName?lower_case}:${functionName}:del")
    @Log(title = "删除${entity}", businessType = BusinessTypeEnum.REMOVE)
    <#else>
    @Log(title = "删除${entity}", businessType = BusinessTypeEnum.REMOVE)
    </#if>
    @PostMapping("/del")
    @ApiOperation(value = "删除${entity}对象", notes = "删除${table.comment!}", response = Result.class)
    public Result<Boolean> delete${entity}(@Valid @RequestBody IdParam idParam) {
        boolean flag = ${serviceName}.remove${entity}ById(idParam);
        return Result.status(flag);
    }

    /**
    * 批量删除${table.comment!}
    */
    <#if cfg.permissionEnabled>
    @RequiresPermissions("${package.ModuleName?lower_case}:${functionName}:del")
    @Log(title = "批量删除${entity}", businessType = BusinessTypeEnum.REMOVE)
    <#else>
    @Log(title = "批量删除${entity}", businessType = BusinessTypeEnum.REMOVE)
    </#if>
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除${entity}对象", notes = "批量删除${table.comment!}", response = Result.class)
    public Result<Boolean> delete${entity}s(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = ${serviceName}.remove${entity}ByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
    * 获取${table.comment!}
    */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取${entity}Vo对象详情", notes = "${table.comment!}", response = ${entity}.class)
    public Result<${entity}> get${entity}(@Valid @RequestBody IdParam idParam) {
        ${entity} ${entityName} = ${serviceName}.get${entity}ById(idParam);
        return Result.success(${entityName});
    }

    /**
    * ${entity}查询列表
    */
    <#if cfg.permissionEnabled>
    @RequiresPermissions("${package.ModuleName?lower_case}:${functionName}:list")
    @PostMapping("/list")
    <#else>
    @PostMapping("/list")
    </#if>
    @ApiOperation(value = "获取${entity}列表", notes = "${entity}列表", response = ${entity}.class)
    public Result<List<${entity}QueryVo>> list${entity}() {
        List<${entity}QueryVo> ${entityName}List = ${serviceName}.list${entity}QueryVo();
        return Result.success(${entityName}List);
    }

    /**
    * ${table.comment!}分页查询
    */
    <#if cfg.permissionEnabled>
    @RequiresPermissions("${package.ModuleName.toLowerCase()}:${functionName}:list")
    @PostMapping("/page")
    <#else>
    @PostMapping("/page")
    </#if>
    @ApiOperation(value = "获取${entity}QueryVo分页列表", notes = "${table.comment!}分页列表", response = ${entity}QueryVo.class)
    public Result<IPage<${entity}QueryVo>> page${entity}(@Valid @RequestBody(required = false) ${entity}QueryPageParam ${entityName}QueryPageParam) {
        IPage<${entity}QueryVo> paging = ${serviceName}.page${entity}QueryVo(${entityName}QueryPageParam);
        return Result.success(paging);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
</#if>
</#if>
