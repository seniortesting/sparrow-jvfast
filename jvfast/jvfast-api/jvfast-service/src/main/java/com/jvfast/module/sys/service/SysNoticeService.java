package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysNotice;
import com.jvfast.module.sys.model.param.SysNoticeAddParam;
import com.jvfast.module.sys.model.param.SysNoticeQueryPageParam;
import com.jvfast.module.sys.model.param.SysNoticeUpdateParam;
import com.jvfast.module.sys.model.vo.SysNoticeQueryVo;

import java.util.List;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 添加SysNotice
     *
     * @param sysNotice
     * @return
     */
    boolean saveSysNotice(SysNoticeAddParam sysNotice);

    /**
     * 通过id更新SysNotice
     *
     * @param sysNotice
     * @return
     */
    boolean updateSysNoticeById(SysNoticeUpdateParam sysNotice);

    /**
     * 通过id删除SysNotice
     *
     * @param idParam
     * @return
     */
    boolean removeSysNoticeById(IdParam idParam);

    boolean removeSysNoticesById(IdBatchParam idParam);

    /**
     * 通过id查询SysNotice
     *
     * @param idParam
     * @return
     */
    SysNotice getSysNoticeById(IdParam idParam);


    /**
     * 查询SysNoticeQueryVo的所有结果
     *
     * @return
     */
    List<SysNoticeQueryVo> listSysNoticeQueryVo();

    /**
     * 查询SysNoticeQueryVo的分页结果
     *
     * @param sysNoticeQueryPageParam
     * @return
     */
    IPage<SysNoticeQueryVo> pageSysNoticeQueryVo(SysNoticeQueryPageParam sysNoticeQueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
