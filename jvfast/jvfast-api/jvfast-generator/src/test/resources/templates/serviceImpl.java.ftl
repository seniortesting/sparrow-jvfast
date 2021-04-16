package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${cfg.idParamPath}.IdParam;
import ${cfg.idParamPath}.IdBatchParam;
import ${cfg.queryParamPackage}.${entity}QueryPageParam;
import ${cfg.queryParamPackage}.${entity}AddParam;
import ${cfg.queryParamPackage}.${entity}UpdateParam;
import ${cfg.queryVoPackage}.${entity}QueryVo;
import ${cfg.entityConverterPackage}.${entity}Converter;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * ${table.comment!}服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Slf4j
@RequiredArgsConstructor
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}>implements ${table.serviceName} {
<#assign mapperName = table.mapperName?substring(0,1)?lower_case + table.mapperName?substring(1)>
<#assign entityName = entity?substring(0,1)?lower_case + entity?substring(1)>
    private final ${table.mapperName} ${mapperName};

    /**
     * 添加${entity}对象
     *
     * @param ${entityName}AddParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean save${entity}(${entity}AddParam ${entityName}AddParam) {
        log.info("传递的${entity}AddParam参数是: {}", ${entityName}AddParam.toString());
        // 对象转换
        ${entity} ${entityName} = ${entity}Converter.INSTANCE.convert${entity}AddParam(${entityName}AddParam);
        boolean savedSuccess = save(${entityName});
        log.info("保存结果是: {},数据Id是: {}", savedSuccess, ${entityName}.getId());
        return savedSuccess;
    }
    /**
     * 通过id更新${entity}对象
     *
     * @param ${entityName}UpdateParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean update${entity}ById(${entity}UpdateParam ${entityName}UpdateParam) {
        log.info("传递的${entity}UpdateParam参数是: {}", ${entityName}UpdateParam.toString());
        //对象转换
        ${entity} ${entityName} = ${entity}Converter.INSTANCE.convert${entity}UpdateParam(${entityName}UpdateParam);
        boolean updateSuccess = updateById(${entityName});
        log.info("更新结果是: {},数据Id是: {}", updateSuccess, ${entityName}.getId());
        return updateSuccess;
    }
    /**
     * 通过id删除${entity}对象
     *
     * @param idParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean remove${entity}ById(IdParam idParam) {
        log.info("删除IdParam参数是: {}", idParam.toString());
        Long id = idParam.getId();
        boolean removeSuccess = removeById(id);
        log.info("删除结果是: {},数据Id是: {}", removeSuccess, id);
        return removeSuccess;
    }
    /**
     * 通过id批量删除${entity}对象
     *
     * @param idBatchParam
     * @return boolean
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean remove${entity}ByIds(IdBatchParam idBatchParam) {
        log.info("批量删除IdBatchParam参数是: {}", idBatchParam.toString());
        List<Long>ids = idBatchParam.getIds();
        boolean removeSuccess = removeByIds(ids);
        log.info("删除结果是: {},数据Id是: {}", removeSuccess, ids.toString());
        return removeSuccess;
    }
    /**
     * 通过id查询${entity}对象
     *
     * @param idParam
     * @return ${entity}
    */
    @Override
    public ${entity} get${entity}ById(IdParam idParam) {
        Long id = idParam.getId();
        ${entity} ${entityName} = getById(id);
        return ${entityName};
    }
    /**
     * 查询${entity}QueryVo的所有结果
     *
     * @return List<${entity}QueryVo>
    */
    @Override
    public List<${entity}QueryVo> list${entity}QueryVo() {
        LambdaQueryWrapper<${entity}> lambdaQueryWrapper = Wrappers.<${entity}>lambdaQuery();
        List<${entity}>list${entityName} = list(lambdaQueryWrapper);
        List<${entity}QueryVo> list${entity}QueryVo = ${entity}Converter.INSTANCE.convert${entity}List(list${entityName});
        return list${entity}QueryVo;
    }
    /**
     * 查询${entity}QueryVo的分页结果
     *
     * @param ${entityName}QueryPageParam
     * @return IPage<${entity}QueryVo>
    */
    @Override
    public IPage<${entity}QueryVo> page${entity}QueryVo(${entity}QueryPageParam ${entityName}QueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = ${entityName}QueryPageParam.getPageNo();
        Integer pageSize = ${entityName}QueryPageParam.getPageSize();
        String keyword = ${entityName}QueryPageParam.getKeyword();
        log.info("分页查询请求参数: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo,pageSize,true);
        // 查询分页结果
        List<${entity}QueryVo> list${entity}QueryVo = ${mapperName}.page${entity}(pagingData, ${entityName}QueryPageParam);
        pagingData.setRecords(list${entity}QueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
</#if>
