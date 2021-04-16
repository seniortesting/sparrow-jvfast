package com.jvfast.module.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.monitor.converter.MonitorLoginHistoryConverter;
import com.jvfast.module.monitor.mapper.MonitorLoginHistoryMapper;
import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryAddParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryQueryPageParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLoginHistoryQueryVo;
import com.jvfast.module.monitor.service.MonitorLoginHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class MonitorLoginHistoryServiceImpl extends ServiceImpl<MonitorLoginHistoryMapper, MonitorLoginHistory> implements MonitorLoginHistoryService {
    private final MonitorLoginHistoryMapper monitorLoginHistoryMapper;

    /**
     * 添加MonitorLoginHistory
     *
     * @param monitorLoginHistoryAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMonitorLoginHistory(MonitorLoginHistoryAddParam monitorLoginHistoryAddParam) {
        // 对象转换
        MonitorLoginHistory monitorLoginHistory = MonitorLoginHistoryConverter.INSTANCE.convertMonitorLoginHistoryAddParam(monitorLoginHistoryAddParam);
        boolean savedResult = save(monitorLoginHistory);
        return savedResult;
    }

    /**
     * 通过id更新MonitorLoginHistory
     *
     * @param monitorLoginHistoryUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMonitorLoginHistoryById(MonitorLoginHistoryUpdateParam monitorLoginHistoryUpdateParam) {
        //对象转换
        MonitorLoginHistory monitorLoginHistory = MonitorLoginHistoryConverter.INSTANCE.convertMonitorLoginHistoryUpdateParam(monitorLoginHistoryUpdateParam);
        boolean updatedResult = updateById(monitorLoginHistory);
        return updatedResult;
    }

    /**
     * 通过id删除MonitorLoginHistory
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeMonitorLoginHistoryById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id批量删除MonitorLoginHistory
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeMonitorLoginHistoryByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询MonitorLoginHistory
     *
     * @param idParam
     * @return
     */
    @Override
    public MonitorLoginHistory getMonitorLoginHistoryById(IdParam idParam) {
        Long id = idParam.getId();
        MonitorLoginHistory getResult = getById(id);
        return getResult;
    }

    /**
     * 查询MonitorLoginHistoryQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<MonitorLoginHistoryQueryVo> listMonitorLoginHistoryQueryVo() {
        LambdaQueryWrapper<MonitorLoginHistory> lambdaQueryWrapper = Wrappers.<MonitorLoginHistory>lambdaQuery();
        List<MonitorLoginHistory> listmonitorLoginHistory = list();
        List<MonitorLoginHistoryQueryVo> listMonitorLoginHistoryQueryVo = MonitorLoginHistoryConverter.INSTANCE.convertMonitorLoginHistoryList(listmonitorLoginHistory);
        return listMonitorLoginHistoryQueryVo;
    }

    /**
     * 查询MonitorLoginHistoryQueryVo的分页结果
     *
     * @param monitorLoginHistoryQueryPageParam
     * @return
     */
    @Override
    public IPage<MonitorLoginHistoryQueryVo> pageMonitorLoginHistoryQueryVo(MonitorLoginHistoryQueryPageParam monitorLoginHistoryQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = monitorLoginHistoryQueryPageParam.getPageNo();
        Integer pageSize = monitorLoginHistoryQueryPageParam.getPageSize();
        String keyword = monitorLoginHistoryQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<MonitorLoginHistoryQueryVo> listMonitorLoginHistoryQueryVo = monitorLoginHistoryMapper.getMonitorLoginHistoryPage(pagingData, monitorLoginHistoryQueryPageParam);
        pagingData.setRecords(listMonitorLoginHistoryQueryVo);
        return pagingData;
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
