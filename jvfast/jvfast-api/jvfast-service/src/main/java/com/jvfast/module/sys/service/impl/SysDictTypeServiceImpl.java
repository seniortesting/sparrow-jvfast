package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysDictTypeConverter;
import com.jvfast.module.sys.mapper.SysDictTypeMapper;
import com.jvfast.module.sys.model.entity.SysDictType;
import com.jvfast.module.sys.model.param.SysDictTypeAddParam;
import com.jvfast.module.sys.model.param.SysDictTypeQueryPageParam;
import com.jvfast.module.sys.model.param.SysDictTypeUpdateParam;
import com.jvfast.module.sys.model.vo.SysDictTypeQueryVo;
import com.jvfast.module.sys.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 系统字典类型表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {


    private final SysDictTypeMapper sysDictTypeMapper;

    /**
     * 添加SysDictType
     *
     * @param sysDictTypeAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysDictType(SysDictTypeAddParam sysDictTypeAddParam) {
        // 对象转换
        SysDictType sysDictType = SysDictTypeConverter.INSTANCE.convertSysDictTypeAddParam(sysDictTypeAddParam);
        boolean savedResult = save(sysDictType);
        return savedResult;
    }

    /**
     * 通过id更新SysDictType
     *
     * @param sysDictTypeUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysDictTypeById(SysDictTypeUpdateParam sysDictTypeUpdateParam) {
        //对象转换
        SysDictType sysDictType = SysDictTypeConverter.INSTANCE.convertSysDictTypeUpdateParam(sysDictTypeUpdateParam);
        boolean updatedResult = updateById(sysDictType);
        return updatedResult;
    }

    /**
     * 通过id删除SysDictType
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysDictTypeById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    @Override
    public boolean removeSysDictTypesById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysDictType
     *
     * @param idParam
     * @return
     */
    @Override
    public SysDictType getSysDictTypeById(IdParam idParam) {
        Long id = idParam.getId();
        SysDictType getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysDictTypeQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysDictTypeQueryVo> listSysDictTypeQueryVo() {
        // 获取启用的类型
        LambdaQueryWrapper<SysDictType> dictTypeLambdaQueryWrapper = Wrappers.<SysDictType>lambdaQuery()
                .eq(SysDictType::getStatus, true);
        List<SysDictType> listsysDictType = list(dictTypeLambdaQueryWrapper);
        List<SysDictTypeQueryVo> listSysDictTypeQueryVo = SysDictTypeConverter.INSTANCE.convertSysDictTypeList(listsysDictType);
        return listSysDictTypeQueryVo;
    }

    /**
     * 查询SysDictTypeQueryVo的分页结果
     *
     * @param sysDictTypeQueryPageParam
     * @return
     */
    @Override
    public IPage<SysDictTypeQueryVo> pageSysDictTypeQueryVo(SysDictTypeQueryPageParam sysDictTypeQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysDictTypeQueryPageParam.getPageNo();
        Integer pageSize = sysDictTypeQueryPageParam.getPageSize();
        String keyword = sysDictTypeQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysDictTypeQueryVo> listSysDictTypeQueryVo = sysDictTypeMapper.getSysDictTypePage(pagingData, sysDictTypeQueryPageParam);
        pagingData.setRecords(listSysDictTypeQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
