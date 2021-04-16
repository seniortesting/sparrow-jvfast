package com.jvfast.module.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.monitor.converter.MonitorLogConverter;
import com.jvfast.module.monitor.mapper.MonitorLogMapper;
import com.jvfast.module.monitor.model.entity.MonitorLog;
import com.jvfast.module.monitor.model.param.MonitorLogAddParam;
import com.jvfast.module.monitor.model.param.MonitorLogQueryPageParam;
import com.jvfast.module.monitor.model.param.MonitorLogUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLogQueryVo;
import com.jvfast.module.monitor.service.MonitorLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class MonitorLogServiceImpl extends ServiceImpl<MonitorLogMapper, MonitorLog> implements MonitorLogService {
    private final MonitorLogMapper monitorLogMapper;

    /**
     * 添加MonitorLog
     *
     * @param monitorLogAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMonitorLog(MonitorLogAddParam monitorLogAddParam) {
        // 对象转换
        MonitorLog monitorLog = MonitorLogConverter.INSTANCE.convertMonitorLogAddParam(monitorLogAddParam);
        boolean savedResult = save(monitorLog);
        return savedResult;
    }

    @Override
    public boolean saveMonitorLogBatch(List<MonitorLogAddParam> monitorLogs) {
        List<MonitorLog> monitorLog = MonitorLogConverter.INSTANCE.convertMonitorLogAddParams(monitorLogs);
        boolean savedResult = saveBatch(monitorLog);
        return savedResult;
    }

    /**
     * 通过id更新MonitorLog
     *
     * @param monitorLogUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMonitorLogById(MonitorLogUpdateParam monitorLogUpdateParam) {
        //对象转换
        MonitorLog monitorLog = MonitorLogConverter.INSTANCE.convertMonitorLogUpdateParam(monitorLogUpdateParam);
        boolean updatedResult = updateById(monitorLog);
        return updatedResult;
    }

    /**
     * 通过id删除MonitorLog
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeMonitorLogById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id批量删除MonitorLog
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeMonitorLogByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询MonitorLog
     *
     * @param idParam
     * @return
     */
    @Override
    public MonitorLog getMonitorLogById(IdParam idParam) {
        Long id = idParam.getId();
        MonitorLog getResult = getById(id);
        return getResult;
    }

    /**
     * 查询MonitorLogQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<MonitorLogQueryVo> listMonitorLogQueryVo() {
        LambdaQueryWrapper<MonitorLog> lambdaQueryWrapper = Wrappers.<MonitorLog>lambdaQuery();
        List<MonitorLog> listmonitorLog = list();
        List<MonitorLogQueryVo> listMonitorLogQueryVo = MonitorLogConverter.INSTANCE.convertMonitorLogList(listmonitorLog);
        return listMonitorLogQueryVo;
    }

    /**
     * 查询MonitorLogQueryVo的分页结果
     *
     * @param monitorLogQueryPageParam
     * @return
     */
    @Override
    public IPage<MonitorLogQueryVo> pageMonitorLogQueryVo(MonitorLogQueryPageParam monitorLogQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = monitorLogQueryPageParam.getPageNo();
        Integer pageSize = monitorLogQueryPageParam.getPageSize();
        String keyword = monitorLogQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<MonitorLogQueryVo> listMonitorLogQueryVo = monitorLogMapper.getMonitorLogPage(pagingData, monitorLogQueryPageParam);
        pagingData.setRecords(listMonitorLogQueryVo);
        return pagingData;
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
