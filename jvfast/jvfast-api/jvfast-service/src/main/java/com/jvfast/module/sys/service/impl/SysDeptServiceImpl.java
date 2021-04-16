package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.constant.SysConst;
import com.jvfast.common.entity.TreeManager;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysDeptConverter;
import com.jvfast.module.sys.mapper.SysDeptMapper;
import com.jvfast.module.sys.model.entity.SysDept;
import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.param.SysDeptAddParam;
import com.jvfast.module.sys.model.param.SysDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysDeptQueryVo;
import com.jvfast.module.sys.service.SysDeptService;
import com.jvfast.module.sys.service.SysUserDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {


    private final SysDeptMapper sysDeptMapper;
    private final SysUserDeptService sysUserDeptService;

    /**
     * 添加SysDept
     *
     * @param sysDeptAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysDept(SysDeptAddParam sysDeptAddParam) {
        // 对象转换
        SysDept sysDept = SysDeptConverter.INSTANCE.convertSysDeptAddParam(sysDeptAddParam);
        boolean savedResult = save(sysDept);
        return savedResult;
    }

    /**
     * 通过id更新SysDept
     *
     * @param sysDeptUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysDeptById(SysDeptUpdateParam sysDeptUpdateParam) {
        //对象转换
        SysDept sysDept = SysDeptConverter.INSTANCE.convertSysDeptUpdateParam(sysDeptUpdateParam);
        boolean updatedResult = updateById(sysDept);
        return updatedResult;
    }

    /**
     * 通过id删除SysDept
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysDeptById(IdParam idParam) {
        Long id = idParam.getId();
        List<SysDeptQueryVo> deptTreeChildren = sysDeptMapper.getDeptTreeChildren(id);
        List<Long> deptIds = deptTreeChildren.stream().map(dept -> dept.getId()).collect(Collectors.toList());
        boolean removedResult = removeByIds(deptIds);
        if (removedResult) {
            LambdaQueryWrapper<SysUserDept> sysUserDeptLambdaQueryWrapper = Wrappers.<SysUserDept>lambdaQuery()
                    .select(SysUserDept::getId, SysUserDept::getUserId, SysUserDept::getDeptId)
                    .eq(SysUserDept::getDeptId, id);
            sysUserDeptService.remove(sysUserDeptLambdaQueryWrapper);
        }
        return removedResult;
    }

    @Override
    public boolean removeSysDeptsById(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysDept
     *
     * @param idParam
     * @return
     */
    @Override
    public SysDept getSysDeptById(IdParam idParam) {
        Long id = idParam.getId();
        SysDept getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysDeptQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysDeptQueryVo> listSysDeptQueryVo() {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = Wrappers.<SysDept>lambdaQuery();
        List<SysDept> listsysDept = list(lambdaQueryWrapper);
        List<SysDeptQueryVo> listSysDeptQueryVo = SysDeptConverter.INSTANCE.convertSysDeptList(listsysDept);
        return listSysDeptQueryVo;
    }

    /**
     * 查询SysDeptQueryVo的分页结果
     *
     * @param sysDeptQueryPageParam
     * @return
     */
    @Override
    public IPage<SysDeptQueryVo> pageSysDeptQueryVo(SysDeptQueryPageParam sysDeptQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysDeptQueryPageParam.getPageNo();
        Integer pageSize = sysDeptQueryPageParam.getPageSize();
        String keyword = sysDeptQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysDeptQueryVo> listSysDeptQueryVo = sysDeptMapper.getSysDeptPage(pagingData, sysDeptQueryPageParam);
        pagingData.setRecords(listSysDeptQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public List<SysDeptQueryVo> getDeptTreeList() {
        List<SysDeptQueryVo> deptTree = sysDeptMapper.getDeptTree();
        List<SysDeptQueryVo> deptQueryVoList = TreeManager.merge(deptTree, SysConst.DEFAULT_PARENT_ID);
        return deptQueryVoList;
    }
}
