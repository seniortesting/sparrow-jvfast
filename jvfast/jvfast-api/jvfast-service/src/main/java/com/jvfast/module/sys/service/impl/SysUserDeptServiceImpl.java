package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysUserDeptConverter;
import com.jvfast.module.sys.mapper.SysUserDeptMapper;
import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.param.SysUserDeptAddParam;
import com.jvfast.module.sys.model.param.SysUserDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysUserDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserDeptQueryVo;
import com.jvfast.module.sys.service.SysUserDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 用户部门表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {


    private final SysUserDeptMapper sysUserDeptMapper;

    /**
     * 添加SysUserDept
     *
     * @param sysUserDeptAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUserDept(SysUserDeptAddParam sysUserDeptAddParam) {
        // 对象转换
        SysUserDept sysUserDept = SysUserDeptConverter.INSTANCE.convertSysUserDeptAddParam(sysUserDeptAddParam);
        boolean savedResult = save(sysUserDept);
        return savedResult;
    }

    /**
     * 通过id更新SysUserDept
     *
     * @param sysUserDeptUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUserDeptById(SysUserDeptUpdateParam sysUserDeptUpdateParam) {
        //对象转换
        SysUserDept sysUserDept = SysUserDeptConverter.INSTANCE.convertSysUserDeptUpdateParam(sysUserDeptUpdateParam);
        boolean updatedResult = updateById(sysUserDept);
        return updatedResult;
    }

    /**
     * 通过id删除SysUserDept
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysUserDeptById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id查询SysUserDept
     *
     * @param idParam
     * @return
     */
    @Override
    public SysUserDept getSysUserDeptById(IdParam idParam) {
        Long id = idParam.getId();
        SysUserDept getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysUserDeptQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysUserDeptQueryVo> listSysUserDeptQueryVo() {
        List<SysUserDept> listsysUserDept = list();
        List<SysUserDeptQueryVo> listSysUserDeptQueryVo = SysUserDeptConverter.INSTANCE.convertSysUserDeptList(listsysUserDept);
        return listSysUserDeptQueryVo;
    }

    /**
     * 查询SysUserDeptQueryVo的分页结果
     *
     * @param sysUserDeptQueryPageParam
     * @return
     */
    @Override
    public IPage<SysUserDeptQueryVo> pageSysUserDeptQueryVo(SysUserDeptQueryPageParam sysUserDeptQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysUserDeptQueryPageParam.getPageNo();
        Integer pageSize = sysUserDeptQueryPageParam.getPageSize();
        String keyword = sysUserDeptQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysUserDeptQueryVo> listSysUserDeptQueryVo = sysUserDeptMapper.getSysUserDeptPage(pagingData, sysUserDeptQueryPageParam);
        pagingData.setRecords(listSysUserDeptQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
