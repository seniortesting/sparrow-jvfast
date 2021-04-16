package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysCity;
import com.jvfast.module.sys.model.param.SysCityQueryPageParam;
import com.jvfast.module.sys.model.vo.SysCityQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 * 国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md Mapper 接口
 * <p>
 *
 * @author Walter Hu
 */
@Mapper
public interface SysCityMapper extends BaseMapper<SysCity> {

    /**
     * sys_city分页查询结果
     *
     * @author Walter Hu
     */
    List<SysCityQueryVo> pageSysCity(IPage page, @Param("query") SysCityQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
