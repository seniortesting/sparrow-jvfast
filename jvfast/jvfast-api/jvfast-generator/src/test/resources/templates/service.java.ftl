package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${cfg.queryParamPackage}.${entity}QueryPageParam;
import ${cfg.queryParamPackage}.${entity}AddParam;
import ${cfg.queryParamPackage}.${entity}UpdateParam;
import ${cfg.queryVoPackage}.${entity}QueryVo;
import ${cfg.idParamPath}.IdParam;
import ${cfg.idParamPath}.IdBatchParam;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * <p>
 * ${table.comment!}服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    <#assign entityName = entity?substring(0,1)?lower_case + entity?substring(1)>

    /**
     * 添加${entity}对象
     *
     * @param ${entityName}
     * @return boolean
    */
    boolean save${entity}(${entity}AddParam ${entityName});

    /**
     * 通过id更新${entity}对象
     *
     * @param ${entityName}
     * @return boolean
    */
    boolean update${entity}ById(${entity}UpdateParam ${entityName});

    /**
     * 通过id删除${entity}对象
     *
     * @param idParam
     * @return boolean
    */
    boolean remove${entity}ById(IdParam idParam);

    /**
     * 通过id批量删除${entity}对象
     *
     * @param idBatchParam
     * @return boolean
    */
    boolean remove${entity}ByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询${entity}对象
     *
     * @param idParam
     * @return ${entity}
    */
    ${entity} get${entity}ById(IdParam idParam);

    /**
     * 查询${entity}QueryVo的所有结果
     *
     * @return List<${entity}QueryVo>
    */
    List<${entity}QueryVo> list${entity}QueryVo();

    /**
     * 查询${entity}QueryVo的分页结果
     *
     * @param ${entityName}QueryPageParam
     * @return IPage<${entity}QueryVo>
    */
    IPage<${entity}QueryVo> page${entity}QueryVo(${entity}QueryPageParam ${entityName}QueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/

}

</#if>
