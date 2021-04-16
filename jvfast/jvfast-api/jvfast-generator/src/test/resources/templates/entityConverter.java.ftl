package ${cfg.entityConverterPackage};

import ${package.Entity}.${entity};
import ${cfg.queryVoPackage}.${entity}QueryVo;
import ${cfg.queryParamPackage}.${entity}AddParam;
import ${cfg.queryParamPackage}.${entity}UpdateParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ${entity}对象转换接口
 * @author ${author}
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface ${entity}Converter {

    ${entity}Converter INSTANCE = Mappers.getMapper(${entity}Converter.class);

    /**
     *  添加操作
     *  ${entity}AddParam对象转换为${entity}
     *
     * @param ${cfg.entityName}AddParam
     * @return ${entity}
     */
    ${entity} convert${entity}AddParam(${entity}AddParam ${cfg.entityName}AddParam);

    /**
     *  修改操作
     *  ${entity}UpdateParam对象转换为${entity}
     *
     * @param ${cfg.entityName}UpdateParam
     * @return ${entity}
     */
    ${entity} convert${entity}UpdateParam(${entity}UpdateParam ${cfg.entityName}UpdateParam);

    /**
     *  查询列表转化实体
     *  ${entity}对象转换为${entity}QueryVo
     *
     * @param ${cfg.entityName}
     * @return List<${entity}QueryVo>
     */
    List<${entity}QueryVo> convert${entity}List(List<${entity}> ${cfg.entityName});

    /**
     *  查询分页操作
     *  ${entity}对象转换为${entity}QueryVo
     *
     * @param ${cfg.entityName}
     * @return ${entity}QueryVo
     */
    ${entity}QueryVo convert${entity}(${entity} ${cfg.entityName});

}
