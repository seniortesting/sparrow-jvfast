package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysNews;
import com.jvfast.module.sys.model.param.SysNewsAddParam;
import com.jvfast.module.sys.model.param.SysNewsQueryPageParam;
import com.jvfast.module.sys.model.param.SysNewsUpdateParam;
import com.jvfast.module.sys.model.vo.SysNewsQueryVo;

import java.util.List;

/**
 * <p>
 * 消息新闻表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
public interface SysNewsService extends IService<SysNews> {

    /**
     * 添加SysNews
     *
     * @param sysNews
     * @return
     */
    boolean saveSysNews(SysNewsAddParam sysNews);

    /**
     * 通过id更新SysNews
     *
     * @param sysNews
     * @return
     */
    boolean updateSysNewsById(SysNewsUpdateParam sysNews);

    /**
     * 通过id删除SysNews
     *
     * @param idParam
     * @return
     */
    boolean removeSysNewsById(IdParam idParam);

    boolean removeSysNewssById(IdBatchParam idParam);

    /**
     * 通过id查询SysNews
     *
     * @param idParam
     * @return
     */
    SysNews getSysNewsById(IdParam idParam);


    /**
     * 查询SysNewsQueryVo的所有结果
     *
     * @return
     */
    List<SysNewsQueryVo> listSysNewsQueryVo();

    /**
     * 查询SysNewsQueryVo的分页结果
     *
     * @param sysNewsQueryPageParam
     * @return
     */
    IPage<SysNewsQueryVo> pageSysNewsQueryVo(SysNewsQueryPageParam sysNewsQueryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
