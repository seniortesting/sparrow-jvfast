package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysNewsConverter;
import com.jvfast.module.sys.mapper.SysNewsMapper;
import com.jvfast.module.sys.model.entity.SysNews;
import com.jvfast.module.sys.model.param.SysNewsAddParam;
import com.jvfast.module.sys.model.param.SysNewsQueryPageParam;
import com.jvfast.module.sys.model.param.SysNewsUpdateParam;
import com.jvfast.module.sys.model.vo.SysNewsQueryVo;
import com.jvfast.module.sys.service.SysNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 消息新闻表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysNewsServiceImpl extends ServiceImpl<SysNewsMapper, SysNews> implements SysNewsService {


    private final SysNewsMapper sysNewsMapper;

    /**
     * 添加SysNews
     *
     * @param sysNewsAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysNews(SysNewsAddParam sysNewsAddParam) {
        // 对象转换
        SysNews sysNews = SysNewsConverter.INSTANCE.convertSysNewsAddParam(sysNewsAddParam);
        boolean savedResult = save(sysNews);
        return savedResult;
    }

    /**
     * 通过id更新SysNews
     *
     * @param sysNewsUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysNewsById(SysNewsUpdateParam sysNewsUpdateParam) {
        //对象转换
        SysNews sysNews = SysNewsConverter.INSTANCE.convertSysNewsUpdateParam(sysNewsUpdateParam);
        boolean updatedResult = updateById(sysNews);
        return updatedResult;
    }

    /**
     * 通过id删除SysNews
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysNewsById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    @Override
    public boolean removeSysNewssById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysNews
     *
     * @param idParam
     * @return
     */
    @Override
    public SysNews getSysNewsById(IdParam idParam) {
        Long id = idParam.getId();
        SysNews getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysNewsQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysNewsQueryVo> listSysNewsQueryVo() {
        List<SysNews> listsysNews = list();
        List<SysNewsQueryVo> listSysNewsQueryVo = SysNewsConverter.INSTANCE.convertSysNewsList(listsysNews);
        return listSysNewsQueryVo;
    }

    /**
     * 查询SysNewsQueryVo的分页结果
     *
     * @param sysNewsQueryPageParam
     * @return
     */
    @Override
    public IPage<SysNewsQueryVo> pageSysNewsQueryVo(SysNewsQueryPageParam sysNewsQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = sysNewsQueryPageParam.getPageNo();
        Integer pageSize = sysNewsQueryPageParam.getPageSize();
        String keyword = sysNewsQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysNewsQueryVo> listSysNewsQueryVo = sysNewsMapper.getSysNewsPage(pagingData, sysNewsQueryPageParam);
        pagingData.setRecords(listSysNewsQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
