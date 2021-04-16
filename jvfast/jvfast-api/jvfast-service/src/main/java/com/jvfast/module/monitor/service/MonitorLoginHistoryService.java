package com.jvfast.module.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryAddParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryQueryPageParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLoginHistoryQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
public interface MonitorLoginHistoryService extends IService<MonitorLoginHistory> {

    /**
     * 添加MonitorLoginHistory
     *
     * @param monitorLoginHistory
     * @return
     */
    boolean saveMonitorLoginHistory(MonitorLoginHistoryAddParam monitorLoginHistory);

    /**
     * 通过id更新MonitorLoginHistory
     *
     * @param monitorLoginHistory
     * @return
     */
    boolean updateMonitorLoginHistoryById(MonitorLoginHistoryUpdateParam monitorLoginHistory);

    /**
     * 通过id删除MonitorLoginHistory
     *
     * @param idParam
     * @return
     */
    boolean removeMonitorLoginHistoryById(IdParam idParam);

    /**
     * 通过id批量删除MonitorLoginHistory
     *
     * @param idBatchParam
     * @return
     */
    boolean removeMonitorLoginHistoryByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询MonitorLoginHistory
     *
     * @param idParam
     * @return
     */
    MonitorLoginHistory getMonitorLoginHistoryById(IdParam idParam);

    /**
     * 查询MonitorLoginHistoryQueryVo的所有结果
     *
     * @return
     */
    List<MonitorLoginHistoryQueryVo> listMonitorLoginHistoryQueryVo();

    /**
     * 查询MonitorLoginHistoryQueryVo的分页结果
     *
     * @param monitorLoginHistoryQueryPageParam
     * @return
     */
    IPage<MonitorLoginHistoryQueryVo> pageMonitorLoginHistoryQueryVo(MonitorLoginHistoryQueryPageParam monitorLoginHistoryQueryPageParam) throws DataAccessException;
    /***************************** 以下为扩展接口 ******************************************************/
}
