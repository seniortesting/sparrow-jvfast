package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysNoticeConverter;
import com.jvfast.module.sys.mapper.SysNoticeMapper;
import com.jvfast.module.sys.model.entity.SysNotice;
import com.jvfast.module.sys.model.param.SysNoticeAddParam;
import com.jvfast.module.sys.model.param.SysNoticeQueryPageParam;
import com.jvfast.module.sys.model.param.SysNoticeUpdateParam;
import com.jvfast.module.sys.model.vo.SysNoticeQueryVo;
import com.jvfast.module.sys.service.SysNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {


    private final SysNoticeMapper sysNoticeMapper;

    /**
     * 添加SysNotice
     *
     * @param sysNoticeAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysNotice(SysNoticeAddParam sysNoticeAddParam) {
        // 对象转换
        SysNotice sysNotice = SysNoticeConverter.INSTANCE.convertSysNoticeAddParam(sysNoticeAddParam);
        boolean savedResult = save(sysNotice);
        return savedResult;
    }

    /**
     * 通过id更新SysNotice
     *
     * @param sysNoticeUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysNoticeById(SysNoticeUpdateParam sysNoticeUpdateParam) {
        //对象转换
        SysNotice sysNotice = SysNoticeConverter.INSTANCE.convertSysNoticeUpdateParam(sysNoticeUpdateParam);
        boolean updatedResult = updateById(sysNotice);
        return updatedResult;
    }

    /**
     * 通过id删除SysNotice
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysNoticeById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    @Override
    public boolean removeSysNoticesById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysNotice
     *
     * @param idParam
     * @return
     */
    @Override
    public SysNotice getSysNoticeById(IdParam idParam) {
        Long id = idParam.getId();
        SysNotice getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysNoticeQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysNoticeQueryVo> listSysNoticeQueryVo( ) {
        LambdaQueryWrapper<SysNotice> lambdaQueryWrapper = Wrappers.<SysNotice>lambdaQuery()
                .select(SysNotice::getId, SysNotice::getNoticeTitle, SysNotice::getNoticeContent, SysNotice::getNoticeType, SysNotice::getUpdateTime)
                .orderByDesc(SysNotice::getUpdateTime);
        List<SysNotice> listsysNotice = list(lambdaQueryWrapper);
        List<SysNoticeQueryVo> listSysNoticeQueryVo = SysNoticeConverter.INSTANCE.convertSysNoticeList(listsysNotice);
        return listSysNoticeQueryVo;
    }

    /**
     * 查询SysNoticeQueryVo的分页结果
     *
     * @param sysNoticeQueryPageParam
     * @return
     */
    @Override
    public IPage<SysNoticeQueryVo> pageSysNoticeQueryVo(SysNoticeQueryPageParam sysNoticeQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = sysNoticeQueryPageParam.getPageNo();
        Integer pageSize = sysNoticeQueryPageParam.getPageSize();
        String keyword = sysNoticeQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysNoticeQueryVo> listSysNoticeQueryVo = sysNoticeMapper.getSysNoticePage(pagingData, sysNoticeQueryPageParam);
        pagingData.setRecords(listSysNoticeQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
