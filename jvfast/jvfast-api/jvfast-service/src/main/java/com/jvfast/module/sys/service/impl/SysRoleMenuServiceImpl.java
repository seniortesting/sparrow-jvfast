package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysRoleMenuConverter;
import com.jvfast.module.sys.mapper.SysRoleMenuMapper;
import com.jvfast.module.sys.model.entity.SysRoleMenu;
import com.jvfast.module.sys.model.param.SysRoleMenuAddParam;
import com.jvfast.module.sys.model.param.SysRoleMenuQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleMenuQueryVo;
import com.jvfast.module.sys.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 添加SysRoleMenu
     *
     * @param sysRoleMenuAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRoleMenu(SysRoleMenuAddParam sysRoleMenuAddParam) {
        // 对象转换
        SysRoleMenu sysRoleMenu = SysRoleMenuConverter.INSTANCE.convertSysRoleMenuAddParam(sysRoleMenuAddParam);
        boolean savedResult = save(sysRoleMenu);
        return savedResult;
    }

    /**
     * 通过id更新SysRoleMenu
     *
     * @param sysRoleMenuUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysRoleMenuById(SysRoleMenuUpdateParam sysRoleMenuUpdateParam) {
        //对象转换
        SysRoleMenu sysRoleMenu = SysRoleMenuConverter.INSTANCE.convertSysRoleMenuUpdateParam(sysRoleMenuUpdateParam);
        boolean updatedResult = updateById(sysRoleMenu);
        return updatedResult;
    }

    /**
     * 通过id删除SysRoleMenu
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysRoleMenuById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id查询SysRoleMenu
     *
     * @param idParam
     * @return
     */
    @Override
    public SysRoleMenu getSysRoleMenuById(IdParam idParam) {
        Long id = idParam.getId();
        SysRoleMenu getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysRoleMenuQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysRoleMenuQueryVo> listSysRoleMenuQueryVo() {
        List<SysRoleMenu> listsysRoleMenu = list();
        List<SysRoleMenuQueryVo> listSysRoleMenuQueryVo = SysRoleMenuConverter.INSTANCE.convertSysRoleMenuList(listsysRoleMenu);
        return listSysRoleMenuQueryVo;
    }

    /**
     * 查询SysRoleMenuQueryVo的分页结果
     *
     * @param sysRoleMenuQueryPageParam
     * @return
     */
    @Override
    public IPage<SysRoleMenuQueryVo> pageSysRoleMenuQueryVo(SysRoleMenuQueryPageParam sysRoleMenuQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysRoleMenuQueryPageParam.getPageNo();
        Integer pageSize = sysRoleMenuQueryPageParam.getPageSize();
        String keyword = sysRoleMenuQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysRoleMenuQueryVo> listSysRoleMenuQueryVo = sysRoleMenuMapper.getSysRoleMenuPage(pagingData, sysRoleMenuQueryPageParam);
        pagingData.setRecords(listSysRoleMenuQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
