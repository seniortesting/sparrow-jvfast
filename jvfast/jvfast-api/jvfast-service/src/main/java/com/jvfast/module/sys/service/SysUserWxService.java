package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.model.param.*;
import com.jvfast.module.sys.model.vo.SysUserWxQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 微信用户 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-02-03
 */
public interface SysUserWxService extends IService<SysUserWx> {


    /**
     * 添加SysUserWx
     *
     * @param sysUserWx
     * @return
     */
    boolean saveSysUserWx(SysUserWxAddParam sysUserWx);

    /**
     * 通过id更新SysUserWx
     *
     * @param sysUserWx
     * @return
     */
    boolean updateSysUserWxById(SysUserWxUpdateParam sysUserWx);

    /**
     * 通过id删除SysUserWx
     *
     * @param idParam
     * @return
     */
    boolean removeSysUserWxById(IdParam idParam);

    /**
     * 通过id批量删除SysUserWx
     *
     * @param idBatchParam
     * @return
     */
    boolean removeSysUserWxByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询SysUserWx
     *
     * @param idParam
     * @return
     */
    SysUserWx getSysUserWxById(IdParam idParam);

    /**
     * 查询SysUserWxQueryVo的所有结果
     *
     * @return
     */
    List<SysUserWxQueryVo> listSysUserWxQueryVo();

    /**
     * 查询SysUserWxQueryVo的分页结果
     *
     * @param sysUserWxQueryPageParam
     * @return
     */
    IPage<SysUserWxQueryVo> pageSysUserWxQueryVo(SysUserWxQueryPageParam sysUserWxQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    SysUserWxQueryVo getUserWxByOpenId(SysUserWxOpenIdQueryParam sysUserWxOpenIdQueryParam);

    Boolean bindWpUser(SysUserWxBindingParam sysUserWxBindingParam);

    SysUserWxQueryVo getBindStatus(SysUserWxBindingStatusParam bindingParam);

}

