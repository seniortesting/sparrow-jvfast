package com.jvfast.module.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryQueryPageParam;
import com.jvfast.module.monitor.model.vo.MonitorLoginHistoryQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统访问记录 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
@Mapper
public interface MonitorLoginHistoryMapper extends BaseMapper<MonitorLoginHistory> {

    /**
     * <p>
     * monitor_login_history分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-12-17
     */
    List<MonitorLoginHistoryQueryVo> getMonitorLoginHistoryPage(IPage page, @Param("query") MonitorLoginHistoryQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
