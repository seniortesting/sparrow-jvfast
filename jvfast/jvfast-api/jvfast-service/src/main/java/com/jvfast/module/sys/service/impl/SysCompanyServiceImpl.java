package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysCompanyConverter;
import com.jvfast.module.sys.mapper.SysCompanyMapper;
import com.jvfast.module.sys.model.entity.SysCompany;
import com.jvfast.module.sys.model.param.SysCompanyAddParam;
import com.jvfast.module.sys.model.param.SysCompanyQueryPageParam;
import com.jvfast.module.sys.model.param.SysCompanyUpdateParam;
import com.jvfast.module.sys.model.vo.SysCompanyQueryVo;
import com.jvfast.module.sys.service.SysCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-19
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements SysCompanyService {
        private final SysCompanyMapper sysCompanyMapper;

    /**
     * 添加CvrCompany
     * @param cvrCompanyAddParam
     * @return
    */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCvrCompany(SysCompanyAddParam cvrCompanyAddParam) {
        // 对象转换
        SysCompany sysCompany = SysCompanyConverter.INSTANCE.convertCvrCompanyAddParam(cvrCompanyAddParam);
        boolean savedResult = save(sysCompany);
        return savedResult;
    }
    /**
     * 通过id更新CvrCompany
     * @param sysCompanyUpdateParam
     * @return
    */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCvrCompanyById(SysCompanyUpdateParam sysCompanyUpdateParam) {
        //对象转换
        SysCompany sysCompany = SysCompanyConverter.INSTANCE.convertCvrCompanyUpdateParam(sysCompanyUpdateParam);
        boolean updatedResult = updateById(sysCompany);
        return updatedResult;
    }
    /**
     * 通过id删除CvrCompany
     * @param idParam
     * @return
    */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCvrCompanyById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }
    /**
     * 通过id批量删除CvrCompany
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCvrCompanyByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }
    /**
     * 通过id查询CvrCompany
     * @param idParam
     * @return
    */
    @Override
    public SysCompany getCvrCompanyById(IdParam idParam) {
        Long id = idParam.getId();
        SysCompany getResult = getById(id);
        return getResult;
    }
    /**
     * 查询CvrCompanyQueryVo的所有结果
     * @return
     */
    @Override
    public List<SysCompanyQueryVo> listCvrCompanyQueryVo(){
       LambdaQueryWrapper<SysCompany> lambdaQueryWrapper = Wrappers.<SysCompany>lambdaQuery();
       List<SysCompany> listcvrCompany = list();
       List<SysCompanyQueryVo> listSysCompanyQueryVo = SysCompanyConverter.INSTANCE.convertCvrCompanyList(listcvrCompany);
       return listSysCompanyQueryVo;
    }
    /**
     * 查询CvrCompanyQueryVo的分页结果
     * @param sysCompanyQueryPageParam
     * @return
    */
    @Override
    public IPage<SysCompanyQueryVo> pageCvrCompanyQueryVo(SysCompanyQueryPageParam sysCompanyQueryPageParam) throws DataAccessException{
        // 请求传递的分页参数
        Integer pageNo = sysCompanyQueryPageParam.getPageNo();
        Integer pageSize = sysCompanyQueryPageParam.getPageSize();
        String keyword = sysCompanyQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysCompanyQueryVo> listSysCompanyQueryVo = sysCompanyMapper.pageCvrCompany(pagingData, sysCompanyQueryPageParam);
        pagingData.setRecords(listSysCompanyQueryVo);
        return pagingData;
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
