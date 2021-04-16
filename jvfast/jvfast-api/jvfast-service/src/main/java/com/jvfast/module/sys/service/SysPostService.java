package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysPost;
import com.jvfast.module.sys.model.param.SysPostAddParam;
import com.jvfast.module.sys.model.param.SysPostQueryPageParam;
import com.jvfast.module.sys.model.param.SysPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysPostQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 岗位表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 添加SysPost
     *
     * @param sysPostAddParam
     * @return
     */
    boolean saveSysPost(SysPostAddParam sysPostAddParam);

    /**
     * 通过id更新SysPost
     *
     * @param sysPostUpdateParam
     * @return
     */
    boolean updateSysPostById(SysPostUpdateParam sysPostUpdateParam);

    /**
     * 通过id删除SysPost
     *
     * @param idParam
     * @return
     */
    boolean removeSysPostById(IdParam idParam);

    boolean removeSysPostsById(IdBatchParam idParam);

    /**
     * 通过id查询SysPost
     *
     * @param idParam
     * @return
     */
    SysPost getSysPostById(IdParam idParam);


    /**
     * 查询SysPostQueryVo的所有结果
     *
     * @return
     */
    List<SysPostQueryVo> listSysPostQueryVo();

    /**
     * 查询SysPostQueryVo的分页结果
     *
     * @param sysPostQueryPageParam
     * @return
     */
    IPage<SysPostQueryVo> pageSysPostQueryVo(SysPostQueryPageParam sysPostQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
