package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysRoleDeptConverter;
import com.jvfast.module.sys.mapper.SysRoleDeptMapper;
import com.jvfast.module.sys.model.entity.SysRoleDept;
import com.jvfast.module.sys.model.param.SysRoleDeptAddParam;
import com.jvfast.module.sys.model.param.SysRoleDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleDeptQueryVo;
import com.jvfast.module.sys.service.SysRoleDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 角色部门表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {


    private final SysRoleDeptMapper sysRoleDeptMapper;

    /**
     * 添加SysRoleDept
     *
     * @param sysRoleDeptAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRoleDept(SysRoleDeptAddParam sysRoleDeptAddParam) {
        // 对象转换
        SysRoleDept sysRoleDept = SysRoleDeptConverter.INSTANCE.convertSysRoleDeptAddParam(sysRoleDeptAddParam);
        boolean savedResult = save(sysRoleDept);
        return savedResult;
    }

    /**
     * 通过id更新SysRoleDept
     *
     * @param sysRoleDeptUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysRoleDeptById(SysRoleDeptUpdateParam sysRoleDeptUpdateParam) {
        //对象转换
        SysRoleDept sysRoleDept = SysRoleDeptConverter.INSTANCE.convertSysRoleDeptUpdateParam(sysRoleDeptUpdateParam);
        boolean updatedResult = updateById(sysRoleDept);
        return updatedResult;
    }

    /**
     * 通过id删除SysRoleDept
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysRoleDeptById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id查询SysRoleDept
     *
     * @param idParam
     * @return
     */
    @Override
    public SysRoleDept getSysRoleDeptById(IdParam idParam) {
        Long id = idParam.getId();
        SysRoleDept getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysRoleDeptQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysRoleDeptQueryVo> listSysRoleDeptQueryVo() {
        List<SysRoleDept> listsysRoleDept = list();
        List<SysRoleDeptQueryVo> listSysRoleDeptQueryVo = SysRoleDeptConverter.INSTANCE.convertSysRoleDeptList(listsysRoleDept);
        return listSysRoleDeptQueryVo;
    }

    /**
     * 查询SysRoleDeptQueryVo的分页结果
     *
     * @param sysRoleDeptQueryPageParam
     * @return
     */
    @Override
    public IPage<SysRoleDeptQueryVo> pageSysRoleDeptQueryVo(SysRoleDeptQueryPageParam sysRoleDeptQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysRoleDeptQueryPageParam.getPageNo();
        Integer pageSize = sysRoleDeptQueryPageParam.getPageSize();
        String keyword = sysRoleDeptQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysRoleDeptQueryVo> listSysRoleDeptQueryVo = sysRoleDeptMapper.getSysRoleDeptPage(pagingData, sysRoleDeptQueryPageParam);
        pagingData.setRecords(listSysRoleDeptQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
