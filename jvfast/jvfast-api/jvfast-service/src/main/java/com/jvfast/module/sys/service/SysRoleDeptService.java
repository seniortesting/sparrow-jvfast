package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysRoleDept;
import com.jvfast.module.sys.model.param.SysRoleDeptAddParam;
import com.jvfast.module.sys.model.param.SysRoleDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleDeptQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 角色部门表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysRoleDeptService extends IService<SysRoleDept> {

    /**
     * 添加SysRoleDept
     *
     * @param sysRoleDept
     * @return
     */
    boolean saveSysRoleDept(SysRoleDeptAddParam sysRoleDept);

    /**
     * 通过id更新SysRoleDept
     *
     * @param sysRoleDept
     * @return
     */
    boolean updateSysRoleDeptById(SysRoleDeptUpdateParam sysRoleDept);

    /**
     * 通过id删除SysRoleDept
     *
     * @param idParam
     * @return
     */
    boolean removeSysRoleDeptById(IdParam idParam);

    /**
     * 通过id查询SysRoleDept
     *
     * @param idParam
     * @return
     */
    SysRoleDept getSysRoleDeptById(IdParam idParam);


    /**
     * 查询SysRoleDeptQueryVo的所有结果
     *
     * @return
     */
    List<SysRoleDeptQueryVo> listSysRoleDeptQueryVo();

    /**
     * 查询SysRoleDeptQueryVo的分页结果
     *
     * @param sysRoleDeptQueryPageParam
     * @return
     */
    IPage<SysRoleDeptQueryVo> pageSysRoleDeptQueryVo(SysRoleDeptQueryPageParam sysRoleDeptQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
