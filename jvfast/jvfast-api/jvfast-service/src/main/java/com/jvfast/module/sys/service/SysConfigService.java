package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysConfig;
import com.jvfast.module.sys.model.param.SysConfigAddParam;
import com.jvfast.module.sys.model.param.SysConfigQueryPageParam;
import com.jvfast.module.sys.model.param.SysConfigUpdateParam;
import com.jvfast.module.sys.model.vo.SysConfigQueryVo;

import java.util.List;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 添加SysConfig
     *
     * @param sysConfig
     * @return
     */
    boolean saveSysConfig(SysConfigAddParam sysConfig);

    /**
     * 通过id更新SysConfig
     *
     * @param sysConfig
     * @return
     */
    boolean updateSysConfigById(SysConfigUpdateParam sysConfig);

    /**
     * 通过id删除SysConfig
     *
     * @param idParam
     * @return
     */
    boolean removeSysConfigById(IdParam idParam);

    boolean removeSysConfigsById(IdBatchParam idParam);

    /**
     * 通过id查询SysConfig
     *
     * @param idParam
     * @return
     */
    SysConfig getSysConfigById(IdParam idParam);


    /**
     * 查询SysConfigQueryVo的所有结果
     *
     * @return
     */
    List<SysConfigQueryVo> listSysConfigQueryVo();

    /**
     * 查询SysConfigQueryVo的分页结果
     *
     * @param sysConfigQueryPageParam
     * @return
     */
    IPage<SysConfigQueryVo> pageSysConfigQueryVo(SysConfigQueryPageParam sysConfigQueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
