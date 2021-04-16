package com.jvfast.module.ignore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.ignore.model.entity.PornMaomi;
import com.jvfast.module.ignore.model.param.PornMaomiQueryPageParam;
import com.jvfast.module.ignore.model.vo.PornMaomiQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 * maomiav.com Mapper 接口
 * <p>
 *
 * @author Walter Hu
 */
@Mapper
public interface PornMaomiMapper extends BaseMapper<PornMaomi> {

    /**
     * porn_maomi分页查询结果
     *
     * @author Walter Hu
     */
    List<PornMaomiQueryVo> pagePornMaomi(IPage page, @Param("query") PornMaomiQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    Boolean likePorn(@Param("like") IdParam idParam);
}
