package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysConfigConverter;
import com.jvfast.module.sys.mapper.SysConfigMapper;
import com.jvfast.module.sys.model.entity.SysConfig;
import com.jvfast.module.sys.model.param.SysConfigAddParam;
import com.jvfast.module.sys.model.param.SysConfigQueryPageParam;
import com.jvfast.module.sys.model.param.SysConfigUpdateParam;
import com.jvfast.module.sys.model.vo.SysConfigQueryVo;
import com.jvfast.module.sys.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {


    private final SysConfigMapper sysConfigMapper;

    /**
     * 添加SysConfig
     *
     * @param sysConfigAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysConfig(SysConfigAddParam sysConfigAddParam) {
        // 对象转换
        SysConfig sysConfig = SysConfigConverter.INSTANCE.convertSysConfigAddParam(sysConfigAddParam);
        boolean savedResult = save(sysConfig);
        return savedResult;
    }

    /**
     * 通过id更新SysConfig
     *
     * @param sysConfigUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysConfigById(SysConfigUpdateParam sysConfigUpdateParam) {
        //对象转换
        SysConfig sysConfig = SysConfigConverter.INSTANCE.convertSysConfigUpdateParam(sysConfigUpdateParam);
        boolean updatedResult = updateById(sysConfig);
        return updatedResult;
    }

    /**
     * 通过id删除SysConfig
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysConfigById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    @Override
    public boolean removeSysConfigsById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysConfig
     *
     * @param idParam
     * @return
     */
    @Override
    public SysConfig getSysConfigById(IdParam idParam) {
        Long id = idParam.getId();
        SysConfig getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysConfigQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysConfigQueryVo> listSysConfigQueryVo() {
        List<SysConfig> listsysConfig = list();
        List<SysConfigQueryVo> listSysConfigQueryVo = SysConfigConverter.INSTANCE.convertSysConfigList(listsysConfig);
        return listSysConfigQueryVo;
    }

    /**
     * 查询SysConfigQueryVo的分页结果
     *
     * @param sysConfigQueryPageParam
     * @return
     */
    @Override
    public IPage<SysConfigQueryVo> pageSysConfigQueryVo(SysConfigQueryPageParam sysConfigQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = sysConfigQueryPageParam.getPageNo();
        Integer pageSize = sysConfigQueryPageParam.getPageSize();
        String keyword = sysConfigQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysConfigQueryVo> listSysConfigQueryVo = sysConfigMapper.getSysConfigPage(pagingData, sysConfigQueryPageParam);
        pagingData.setRecords(listSysConfigQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
