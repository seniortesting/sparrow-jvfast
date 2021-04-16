package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.param.SysUserDeptAddParam;
import com.jvfast.module.sys.model.param.SysUserDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysUserDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserDeptQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 用户部门表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysUserDeptService extends IService<SysUserDept> {

    /**
     * 添加SysUserDept
     *
     * @param sysUserDept
     * @return
     */
    boolean saveSysUserDept(SysUserDeptAddParam sysUserDept);

    /**
     * 通过id更新SysUserDept
     *
     * @param sysUserDept
     * @return
     */
    boolean updateSysUserDeptById(SysUserDeptUpdateParam sysUserDept);

    /**
     * 通过id删除SysUserDept
     *
     * @param idParam
     * @return
     */
    boolean removeSysUserDeptById(IdParam idParam);

    /**
     * 通过id查询SysUserDept
     *
     * @param idParam
     * @return
     */
    SysUserDept getSysUserDeptById(IdParam idParam);


    /**
     * 查询SysUserDeptQueryVo的所有结果
     *
     * @return
     */
    List<SysUserDeptQueryVo> listSysUserDeptQueryVo();

    /**
     * 查询SysUserDeptQueryVo的分页结果
     *
     * @param sysUserDeptQueryPageParam
     * @return
     */
    IPage<SysUserDeptQueryVo> pageSysUserDeptQueryVo(SysUserDeptQueryPageParam sysUserDeptQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
