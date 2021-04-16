package com.jvfast.module.monitor.converter;

import com.jvfast.common.entity.BusinessLog;
import com.jvfast.module.monitor.model.entity.MonitorLog;
import com.jvfast.module.monitor.model.param.MonitorLogAddParam;
import com.jvfast.module.monitor.model.param.MonitorLogUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLogQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MonitorLog对象转换接口
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface MonitorLogConverter {

    MonitorLogConverter INSTANCE = Mappers.getMapper(MonitorLogConverter.class);

    MonitorLog convertLogToMonitorLog(BusinessLog businessLog);

    /**
     * 添加操作
     * MonitorLogAddParam对象转换为MonitorLog
     *
     * @param monitorLogAddParam
     * @return
     */
    MonitorLog convertMonitorLogAddParam(MonitorLogAddParam monitorLogAddParam);

    List<MonitorLog> convertMonitorLogAddParams(List<MonitorLogAddParam> monitorLogAddParams);

    /**
     * 修改操作
     * MonitorLogUpdateParam对象转换为MonitorLog
     *
     * @param monitorLogUpdateParam
     * @return
     */
    MonitorLog convertMonitorLogUpdateParam(MonitorLogUpdateParam monitorLogUpdateParam);

    /**
     * 查询列表转化实体
     * MonitorLog对象转换为MonitorLogQueryVo
     *
     * @param monitorLog
     * @return
     */
    List<MonitorLogQueryVo> convertMonitorLogList(List<MonitorLog> monitorLog);

    /**
     * 查询分页操作
     * MonitorLog对象转换为MonitorLogQueryVo
     *
     * @param monitorLog
     * @return
     */
    MonitorLogQueryVo convertMonitorLog(MonitorLog monitorLog);


}
