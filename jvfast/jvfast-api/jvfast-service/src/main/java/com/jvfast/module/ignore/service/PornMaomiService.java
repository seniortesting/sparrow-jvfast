package com.jvfast.module.ignore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.ignore.model.entity.PornMaomi;
import com.jvfast.module.ignore.model.param.PornMaomiAddParam;
import com.jvfast.module.ignore.model.param.PornMaomiQueryPageParam;
import com.jvfast.module.ignore.model.param.PornMaomiUpdateParam;
import com.jvfast.module.ignore.model.vo.PornMaomiQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * maomiav.com 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-03-14
 */
public interface PornMaomiService extends IService<PornMaomi> {


    /**
     * 添加PornMaomi
     *
     * @param pornMaomi
     * @return
     */
    boolean savePornMaomi(PornMaomiAddParam pornMaomi);

    /**
     * 通过id更新PornMaomi
     *
     * @param pornMaomi
     * @return
     */
    boolean updatePornMaomiById(PornMaomiUpdateParam pornMaomi);

    /**
     * 通过id删除PornMaomi
     *
     * @param idParam
     * @return
     */
    boolean removePornMaomiById(IdParam idParam);

    /**
     * 通过id批量删除PornMaomi
     *
     * @param idBatchParam
     * @return
     */
    boolean removePornMaomiByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询PornMaomi
     *
     * @param idParam
     * @return
     */
    PornMaomi getPornMaomiById(IdParam idParam);

    /**
     * 查询PornMaomiQueryVo的所有结果
     *
     * @return
     */
    List<PornMaomiQueryVo> listPornMaomiQueryVo();

    /**
     * 查询PornMaomiQueryVo的分页结果
     *
     * @param pornMaomiQueryPageParam
     * @return
     */
    IPage<PornMaomiQueryVo> pagePornMaomiQueryVo(PornMaomiQueryPageParam pornMaomiQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    boolean likePorn(IdParam idParam);

}

