package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.constant.PageConst;
import com.jvfast.common.constant.SysConst;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysNotificationConverter;
import com.jvfast.module.sys.mapper.SysNotificationMapper;
import com.jvfast.module.sys.model.entity.SysNotification;
import com.jvfast.module.sys.model.param.SysNotificationAddParam;
import com.jvfast.module.sys.model.param.SysNotificationQueryPageParam;
import com.jvfast.module.sys.model.param.SysNotificationUpdateParam;
import com.jvfast.module.sys.model.vo.SysNotificationQueryVo;
import com.jvfast.module.sys.service.SysNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息) 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysNotificationServiceImpl extends ServiceImpl<SysNotificationMapper, SysNotification> implements SysNotificationService {
    private final SysNotificationMapper sysNotificationMapper;

    /**
     * 添加SysNotification
     *
     * @param sysNotificationAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysNotification(SysNotificationAddParam sysNotificationAddParam) {
        // 对象转换
        SysNotification sysNotification = SysNotificationConverter.INSTANCE.convertSysNotificationAddParam(sysNotificationAddParam);
        boolean savedResult = save(sysNotification);
        return savedResult;
    }

    /**
     * 通过id更新SysNotification
     *
     * @param sysNotificationUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysNotificationById(SysNotificationUpdateParam sysNotificationUpdateParam) {
        //对象转换
        SysNotification sysNotification = SysNotificationConverter.INSTANCE.convertSysNotificationUpdateParam(sysNotificationUpdateParam);
        boolean updatedResult = updateById(sysNotification);
        return updatedResult;
    }

    /**
     * 通过id删除SysNotification
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysNotificationById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id批量删除SysNotification
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysNotificationByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysNotification
     *
     * @param idParam
     * @return
     */
    @Override
    public SysNotification getSysNotificationById(IdParam idParam) {
        Long id = idParam.getId();
        SysNotification getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysNotificationQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysNotificationQueryVo> listSysNotificationQueryVo(SysNotificationQueryPageParam sysNotificationQueryPageParam) {
        Page pagingData = new Page(PageConst.PAGE_INDEX, PageConst.PAGE_SIZE, true);
        List<SysNotificationQueryVo> listSysNotificationQueryVo = sysNotificationMapper.pageSysNotification(pagingData, sysNotificationQueryPageParam);
        return listSysNotificationQueryVo;
    }

    /**
     * 查询SysNotificationQueryVo的分页结果
     *
     * @param sysNotificationQueryPageParam
     * @return
     */
    @Override
    public IPage<SysNotificationQueryVo> pageSysNotificationQueryVo(SysNotificationQueryPageParam sysNotificationQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysNotificationQueryPageParam.getPageNo();
        Integer pageSize = sysNotificationQueryPageParam.getPageSize();
        String keyword = sysNotificationQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysNotificationQueryVo> listSysNotificationQueryVo = sysNotificationMapper.pageSysNotification(pagingData, sysNotificationQueryPageParam);
        pagingData.setRecords(listSysNotificationQueryVo);
        return pagingData;
    }
    /***************************** 以下为扩展接口 ******************************************************/
    /**
     * 批量已读消息
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean readNotifications(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        List<SysNotification> sysNotificationStream = ids.stream().map(id -> {
            SysNotification sysNotification = new SysNotification();
            sysNotification.setId(id);
            sysNotification.setReadStatus(SysConst.READ);
            return sysNotification;
        }).collect(Collectors.toList());
        boolean updateBatchById = updateBatchById(sysNotificationStream);
        return updateBatchById;
    }
}
