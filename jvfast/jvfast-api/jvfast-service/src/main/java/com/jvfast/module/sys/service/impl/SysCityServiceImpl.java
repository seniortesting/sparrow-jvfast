package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.constant.SysConst;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysCityConverter;
import com.jvfast.module.sys.mapper.SysCityMapper;
import com.jvfast.module.sys.model.entity.SysCity;
import com.jvfast.module.sys.model.param.SysCityAddParam;
import com.jvfast.module.sys.model.param.SysCityQueryPageParam;
import com.jvfast.module.sys.model.param.SysCityUpdateParam;
import com.jvfast.module.sys.model.vo.SysCityQueryVo;
import com.jvfast.module.sys.service.SysCityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-02-07
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysCityServiceImpl extends ServiceImpl<SysCityMapper, SysCity> implements SysCityService {
    private final SysCityMapper sysCityMapper;

    /**
     * 添加SysCity
     *
     * @param sysCityAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysCity(SysCityAddParam sysCityAddParam) {
        // 对象转换
        SysCity sysCity = SysCityConverter.INSTANCE.convertSysCityAddParam(sysCityAddParam);
        boolean savedResult = save(sysCity);
        return savedResult;
    }

    /**
     * 通过id更新SysCity
     *
     * @param sysCityUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysCityById(SysCityUpdateParam sysCityUpdateParam) {
        //对象转换
        SysCity sysCity = SysCityConverter.INSTANCE.convertSysCityUpdateParam(sysCityUpdateParam);
        boolean updatedResult = updateById(sysCity);
        return updatedResult;
    }

    /**
     * 通过id删除SysCity
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysCityById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id批量删除SysCity
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysCityByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysCity
     *
     * @param idParam
     * @return
     */
    @Override
    public SysCity getSysCityById(IdParam idParam) {
        Long id = idParam.getId();
        SysCity getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysCityQueryVo的所有结果
     *
     * @return
     */
    @Cacheable(cacheNames = "areaCode",key="#p0")
    @Override
    public List<SysCityQueryVo> listSysCityQueryVo(String areaCode) {
        LambdaQueryWrapper<SysCity> lambdaQueryCityWrapper;
        if (!SysConst.DEFAULT_PARENT_ID.toString().equals(areaCode)) {
            LambdaQueryWrapper<SysCity> lambdaQueryWrapper = Wrappers.<SysCity>lambdaQuery()
                    .select(SysCity::getId)
                    .eq(SysCity::getAreaCode, areaCode);
            SysCity sysCity = getOne(lambdaQueryWrapper,true);
            lambdaQueryCityWrapper = Wrappers.<SysCity>lambdaQuery()
                    .eq(SysCity::getPid, sysCity.getId());
        } else {
            lambdaQueryCityWrapper = Wrappers.<SysCity>lambdaQuery()
                    .eq(SysCity::getPid, areaCode);
        }
        List<SysCity> listsysCity = list(lambdaQueryCityWrapper);
        List<SysCityQueryVo> listSysCityQueryVo = SysCityConverter.INSTANCE.convertSysCityList(listsysCity);
        return listSysCityQueryVo;
    }

    /**
     * 查询SysCityQueryVo的分页结果
     *
     * @param sysCityQueryPageParam
     * @return
     */
    @Override
    public IPage<SysCityQueryVo> pageSysCityQueryVo(SysCityQueryPageParam sysCityQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysCityQueryPageParam.getPageNo();
        Integer pageSize = sysCityQueryPageParam.getPageSize();
        String keyword = sysCityQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysCityQueryVo> listSysCityQueryVo = sysCityMapper.pageSysCity(pagingData, sysCityQueryPageParam);
        pagingData.setRecords(listSysCityQueryVo);
        return pagingData;
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
