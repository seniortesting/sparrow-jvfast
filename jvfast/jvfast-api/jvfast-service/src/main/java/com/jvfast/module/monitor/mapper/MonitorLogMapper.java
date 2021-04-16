package com.jvfast.module.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.monitor.model.entity.MonitorLog;
import com.jvfast.module.monitor.model.param.MonitorLogQueryPageParam;
import com.jvfast.module.monitor.model.vo.MonitorLogQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
@Mapper
public interface MonitorLogMapper extends BaseMapper<MonitorLog> {

    /**
     * <p>
     * monitor_log分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-12-17
     */
    List<MonitorLogQueryVo> getMonitorLogPage(IPage page, @Param("query") MonitorLogQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
