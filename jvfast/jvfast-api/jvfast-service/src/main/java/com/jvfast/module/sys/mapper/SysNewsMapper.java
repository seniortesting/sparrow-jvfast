package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysNews;
import com.jvfast.module.sys.model.param.SysNewsQueryPageParam;
import com.jvfast.module.sys.model.vo.SysNewsQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 消息新闻表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Mapper
public interface SysNewsMapper extends BaseMapper<SysNews> {

    /**
     * <p>
     * sys_news分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-18
     */
    List<SysNewsQueryVo> getSysNewsPage(IPage page, @Param("query") SysNewsQueryPageParam queryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
