package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysCompany;
import com.jvfast.module.sys.model.param.SysCompanyQueryPageParam;
import com.jvfast.module.sys.model.vo.SysCompanyQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
/**
 * <p>
 * 公司表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-19
 */
@Mapper
public interface SysCompanyMapper extends BaseMapper<SysCompany> {

    /**
    * <p>
    * cvr_company分页查询结果
    * </p>
    *
    * @author Walter Hu
    * @since 2020-01-19
    */
    List<SysCompanyQueryVo> pageCvrCompany(IPage page, @Param("query") SysCompanyQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
