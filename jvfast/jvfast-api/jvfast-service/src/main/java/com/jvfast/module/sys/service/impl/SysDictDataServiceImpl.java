package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysDictDataConverter;
import com.jvfast.module.sys.mapper.SysDictDataMapper;
import com.jvfast.module.sys.model.entity.SysDictData;
import com.jvfast.module.sys.model.param.SysDictDataAddParam;
import com.jvfast.module.sys.model.param.SysDictDataQueryPageParam;
import com.jvfast.module.sys.model.param.SysDictDataUpdateParam;
import com.jvfast.module.sys.model.vo.SysDictDataQueryVo;
import com.jvfast.module.sys.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {


    private final SysDictDataMapper sysDictDataMapper;

    /**
     * 添加SysDict
     *
     * @param sysDictAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysDictData(SysDictDataAddParam sysDictAddParam) {
        // 对象转换
        SysDictData sysDict = SysDictDataConverter.INSTANCE.convertSysDictAddParam(sysDictAddParam);
        boolean savedResult = save(sysDict);
        return savedResult;
    }

    /**
     * 通过id更新SysDict
     *
     * @param sysDictUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysDictDataById(SysDictDataUpdateParam sysDictUpdateParam) {
        //对象转换
        SysDictData sysDict = SysDictDataConverter.INSTANCE.convertSysDictUpdateParam(sysDictUpdateParam);
        boolean updatedResult = updateById(sysDict);
        return updatedResult;
    }

    /**
     * 通过id删除SysDict
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysDictDataById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    @Override
    public boolean removeSysDictDatasById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysDict
     *
     * @param idParam
     * @return
     */
    @Override
    public SysDictData getSysDictDataById(IdParam idParam) {
        Long id = idParam.getId();
        SysDictData getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysDictQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysDictDataQueryVo> listSysDictDataQueryVo() {
        List<SysDictData> listsysDict = list();
        List<SysDictDataQueryVo> listSysDictQueryVo = SysDictDataConverter.INSTANCE.convertSysDictList(listsysDict);
        return listSysDictQueryVo;
    }

    /**
     * 查询SysDictQueryVo的分页结果
     *
     * @param sysDictQueryPageParam
     * @return
     */
    @Override
    public IPage<SysDictDataQueryVo> pageSysDictDataQueryVo(SysDictDataQueryPageParam sysDictQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysDictQueryPageParam.getPageNo();
        Integer pageSize = sysDictQueryPageParam.getPageSize();
        String keyword = sysDictQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysDictDataQueryVo> listSysDictQueryVo = sysDictDataMapper.getSysDictDataPage(pagingData, sysDictQueryPageParam);
        pagingData.setRecords(listSysDictQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
