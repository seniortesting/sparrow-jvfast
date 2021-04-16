package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysFeedbackConverter;
import com.jvfast.module.sys.mapper.SysFeedbackMapper;
import com.jvfast.module.sys.model.entity.SysFeedback;
import com.jvfast.module.sys.model.param.SysFeedbackAddParam;
import com.jvfast.module.sys.model.param.SysFeedbackQueryPageParam;
import com.jvfast.module.sys.model.param.SysFeedbackUpdateParam;
import com.jvfast.module.sys.model.vo.SysFeedbackQueryVo;
import com.jvfast.module.sys.service.SysFeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 建议反馈表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-09
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysFeedbackServiceImpl extends ServiceImpl<SysFeedbackMapper, SysFeedback> implements SysFeedbackService {
    private final SysFeedbackMapper sysFeedbackMapper;

    /**
     * 添加SysFeedback
     *
     * @param sysFeedbackAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysFeedback(SysFeedbackAddParam sysFeedbackAddParam) {
        // 对象转换
        SysFeedback sysFeedback = SysFeedbackConverter.INSTANCE.convertSysFeedbackAddParam(sysFeedbackAddParam);
        boolean savedResult = save(sysFeedback);
        return savedResult;
    }

    /**
     * 通过id更新SysFeedback
     *
     * @param sysFeedbackUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysFeedbackById(SysFeedbackUpdateParam sysFeedbackUpdateParam) {
        //对象转换
        SysFeedback sysFeedback = SysFeedbackConverter.INSTANCE.convertSysFeedbackUpdateParam(sysFeedbackUpdateParam);
        boolean updatedResult = updateById(sysFeedback);
        return updatedResult;
    }

    /**
     * 通过id删除SysFeedback
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysFeedbackById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id批量删除SysFeedback
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysFeedbackByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysFeedback
     *
     * @param idParam
     * @return
     */
    @Override
    public SysFeedback getSysFeedbackById(IdParam idParam) {
        Long id = idParam.getId();
        SysFeedback getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysFeedbackQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysFeedbackQueryVo> listSysFeedbackQueryVo(IdParam idParam) {
        LambdaQueryWrapper<SysFeedback> lambdaQueryWrapper = Wrappers.<SysFeedback>lambdaQuery()
                .select(SysFeedback::getId,
                        SysFeedback::getFeedbackStatus,
                        SysFeedback::getDetail,
                        SysFeedback::getTitle,
                        SysFeedback::getUserId,
                        SysFeedback::getCreateTime,
                        SysFeedback::getUpdateTime)
                .eq(SysFeedback::getUserId, idParam.getId())
                .orderByDesc(SysFeedback::getUpdateTime);
        List<SysFeedback> listsysFeedback = list(lambdaQueryWrapper);
        List<SysFeedbackQueryVo> listSysFeedbackQueryVo = SysFeedbackConverter.INSTANCE.convertSysFeedbackList(listsysFeedback);
        return listSysFeedbackQueryVo;
    }

    /**
     * 查询SysFeedbackQueryVo的分页结果
     *
     * @param sysFeedbackQueryPageParam
     * @return
     */
    @Override
    public IPage<SysFeedbackQueryVo> pageSysFeedbackQueryVo(SysFeedbackQueryPageParam sysFeedbackQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysFeedbackQueryPageParam.getPageNo();
        Integer pageSize = sysFeedbackQueryPageParam.getPageSize();
        String keyword = sysFeedbackQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysFeedbackQueryVo> listSysFeedbackQueryVo = sysFeedbackMapper.pageSysFeedback(pagingData, sysFeedbackQueryPageParam);
        pagingData.setRecords(listSysFeedbackQueryVo);
        return pagingData;
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
