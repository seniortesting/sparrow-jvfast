package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysDictData;
import com.jvfast.module.sys.model.param.SysDictDataQueryPageParam;
import com.jvfast.module.sys.model.vo.SysDictDataQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统字典表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * <p>
     * sys_dict分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysDictDataQueryVo> getSysDictDataPage(IPage page, @Param("query") SysDictDataQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
