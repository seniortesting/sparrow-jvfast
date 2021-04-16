package com.jvfast.module.ignore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.ignore.converter.PornMaomiConverter;
import com.jvfast.module.ignore.mapper.PornMaomiMapper;
import com.jvfast.module.ignore.model.entity.PornMaomi;
import com.jvfast.module.ignore.model.param.PornMaomiAddParam;
import com.jvfast.module.ignore.model.param.PornMaomiQueryPageParam;
import com.jvfast.module.ignore.model.param.PornMaomiUpdateParam;
import com.jvfast.module.ignore.model.vo.PornMaomiQueryVo;
import com.jvfast.module.ignore.service.PornMaomiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * maomiav.com 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-03-14
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PornMaomiServiceImpl extends ServiceImpl<PornMaomiMapper, PornMaomi>implements PornMaomiService {
    private final PornMaomiMapper pornMaomiMapper;

    /**
    * 添加PornMaomi
    *
    * @param pornMaomiAddParam
    * @return
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean savePornMaomi(PornMaomiAddParam pornMaomiAddParam) {
        // 对象转换
        PornMaomi pornMaomi = PornMaomiConverter.INSTANCE.convertPornMaomiAddParam(pornMaomiAddParam);
        boolean savedResult = save(pornMaomi);
        return savedResult;
    }
    /**
    * 通过id更新PornMaomi
    *
    * @param pornMaomiUpdateParam
    * @return
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean updatePornMaomiById(PornMaomiUpdateParam pornMaomiUpdateParam) {
        //对象转换
        PornMaomi pornMaomi = PornMaomiConverter.INSTANCE.convertPornMaomiUpdateParam(pornMaomiUpdateParam);
        boolean updatedResult = updateById(pornMaomi);
        return updatedResult;
    }
    /**
    * 通过id删除PornMaomi
    *
    * @param idParam
    * @return
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean removePornMaomiById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }
    /**
    * 通过id批量删除PornMaomi
    *
    * @param idBatchParam
    * @return
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean removePornMaomiByIds(IdBatchParam idBatchParam) {
        List<Long>ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }
    /**
    * 通过id查询PornMaomi
    *
    * @param idParam
    * @return
    */
    @Override
    public PornMaomi getPornMaomiById(IdParam idParam) {
        Long id = idParam.getId();
        PornMaomi getResult = getById(id);
        return getResult;
    }
    /**
    * 查询PornMaomiQueryVo的所有结果
    *
    * @return
    */
    @Override
    public List<PornMaomiQueryVo> listPornMaomiQueryVo() {
        LambdaQueryWrapper<PornMaomi> lambdaQueryWrapper = Wrappers.<PornMaomi>lambdaQuery();
        List<PornMaomi>listpornMaomi = list(lambdaQueryWrapper);
        List<PornMaomiQueryVo> listPornMaomiQueryVo = PornMaomiConverter.INSTANCE.convertPornMaomiList(listpornMaomi);
        return listPornMaomiQueryVo;
    }
    /**
    * 查询PornMaomiQueryVo的分页结果
    *
    * @param pornMaomiQueryPageParam
    * @return
    */
    @Override
    public IPage<PornMaomiQueryVo> pagePornMaomiQueryVo(PornMaomiQueryPageParam pornMaomiQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = pornMaomiQueryPageParam.getPageNo();
        Integer pageSize = pornMaomiQueryPageParam.getPageSize();
        String keyword = pornMaomiQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo,pageSize,true);
        // 查询分页结果
        List<PornMaomiQueryVo> listPornMaomiQueryVo = pornMaomiMapper.pagePornMaomi(pagingData, pornMaomiQueryPageParam);
        pagingData.setRecords(listPornMaomiQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public boolean likePorn(IdParam idParam) {
        Boolean result = pornMaomiMapper.likePorn(idParam);
        return result;
    }
}
