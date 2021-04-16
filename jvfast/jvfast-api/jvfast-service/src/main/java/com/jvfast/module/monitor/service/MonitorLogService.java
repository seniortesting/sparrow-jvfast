package com.jvfast.module.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.monitor.model.entity.MonitorLog;
import com.jvfast.module.monitor.model.param.MonitorLogAddParam;
import com.jvfast.module.monitor.model.param.MonitorLogQueryPageParam;
import com.jvfast.module.monitor.model.param.MonitorLogUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLogQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
public interface MonitorLogService extends IService<MonitorLog> {

    /**
     * 添加MonitorLog
     *
     * @param monitorLog
     * @return
     */
    boolean saveMonitorLog(MonitorLogAddParam monitorLog);

    boolean saveMonitorLogBatch(List<MonitorLogAddParam> monitorLogs);

    /**
     * 通过id更新MonitorLog
     *
     * @param monitorLog
     * @return
     */
    boolean updateMonitorLogById(MonitorLogUpdateParam monitorLog);

    /**
     * 通过id删除MonitorLog
     *
     * @param idParam
     * @return
     */
    boolean removeMonitorLogById(IdParam idParam);

    /**
     * 通过id批量删除MonitorLog
     *
     * @param idBatchParam
     * @return
     */
    boolean removeMonitorLogByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询MonitorLog
     *
     * @param idParam
     * @return
     */
    MonitorLog getMonitorLogById(IdParam idParam);

    /**
     * 查询MonitorLogQueryVo的所有结果
     *
     * @return
     */
    List<MonitorLogQueryVo> listMonitorLogQueryVo();

    /**
     * 查询MonitorLogQueryVo的分页结果
     *
     * @param monitorLogQueryPageParam
     * @return
     */
    IPage<MonitorLogQueryVo> pageMonitorLogQueryVo(MonitorLogQueryPageParam monitorLogQueryPageParam) throws DataAccessException;
    /***************************** 以下为扩展接口 ******************************************************/
}
