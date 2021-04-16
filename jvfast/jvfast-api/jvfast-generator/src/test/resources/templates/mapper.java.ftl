package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import ${cfg.queryVoPackage}.${entity}QueryVo;
import ${cfg.queryParamPackage}.${entity}QueryPageParam;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
/**
 *
 * ${table.comment!}Mapper接口
 * @author ${author}
*/
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    /**
    * ${entity}QueryVo分页查询结果
    *
    * @param page, queryPageParam
    * @return List<${entity}QueryVo>
    */
    List<${entity}QueryVo> page${entity}(IPage page, @Param("query") ${entity}QueryPageParam queryPageParam) throws DataAccessException;
    /***************************** 以下为扩展接口 ******************************************************/
  }
</#if>
