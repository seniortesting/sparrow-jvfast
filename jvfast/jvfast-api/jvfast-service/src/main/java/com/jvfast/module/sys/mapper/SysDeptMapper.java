package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysDept;
import com.jvfast.module.sys.model.param.SysDeptQueryPageParam;
import com.jvfast.module.sys.model.vo.SysDeptQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * <p>
     * sys_dept分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-22
     */
    List<SysDeptQueryVo> getSysDeptPage(IPage page, @Param("query") SysDeptQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    List<SysDeptQueryVo> getDeptTreeChildren(@Param("id") Long id);

    List<SysDeptQueryVo> getDeptTree();
}
