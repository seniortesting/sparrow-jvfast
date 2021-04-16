package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jvfast.module.monitor.model.entity.QuartzJobLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Quartz任务的执行记录 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-21
 */
@Mapper
public interface QuartzJobLogMapper extends BaseMapper<QuartzJobLog> {
    /***************************** 以下为扩展接口 ******************************************************/
}
