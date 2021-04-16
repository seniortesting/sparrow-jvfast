package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysConfig;
import com.jvfast.module.sys.model.param.SysConfigQueryPageParam;
import com.jvfast.module.sys.model.vo.SysConfigQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 参数配置表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * <p>
     * sys_config分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-18
     */
    List<SysConfigQueryVo> getSysConfigPage(IPage page, @Param("query") SysConfigQueryPageParam queryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
