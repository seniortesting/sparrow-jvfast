package com.jvfast.module.monitor.converter;

import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryAddParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLoginHistoryQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MonitorLoginHistory对象转换接口
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface MonitorLoginHistoryConverter {

    MonitorLoginHistoryConverter INSTANCE = Mappers.getMapper(MonitorLoginHistoryConverter.class);

    /**
     * 添加操作
     * MonitorLoginHistoryAddParam对象转换为MonitorLoginHistory
     *
     * @param monitorLoginHistoryAddParam
     * @return
     */
    MonitorLoginHistory convertMonitorLoginHistoryAddParam(MonitorLoginHistoryAddParam monitorLoginHistoryAddParam);

    /**
     * 修改操作
     * MonitorLoginHistoryUpdateParam对象转换为MonitorLoginHistory
     *
     * @param monitorLoginHistoryUpdateParam
     * @return
     */
    MonitorLoginHistory convertMonitorLoginHistoryUpdateParam(MonitorLoginHistoryUpdateParam monitorLoginHistoryUpdateParam);

    /**
     * 查询列表转化实体
     * MonitorLoginHistory对象转换为MonitorLoginHistoryQueryVo
     *
     * @param monitorLoginHistory
     * @return
     */
    List<MonitorLoginHistoryQueryVo> convertMonitorLoginHistoryList(List<MonitorLoginHistory> monitorLoginHistory);

    /**
     * 查询分页操作
     * MonitorLoginHistory对象转换为MonitorLoginHistoryQueryVo
     *
     * @param monitorLoginHistory
     * @return
     */
    MonitorLoginHistoryQueryVo convertMonitorLoginHistory(MonitorLoginHistory monitorLoginHistory);


}
