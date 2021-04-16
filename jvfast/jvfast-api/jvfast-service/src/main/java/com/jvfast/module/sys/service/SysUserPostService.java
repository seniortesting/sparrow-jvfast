package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysUserPost;
import com.jvfast.module.sys.model.param.SysUserPostAddParam;
import com.jvfast.module.sys.model.param.SysUserPostQueryPageParam;
import com.jvfast.module.sys.model.param.SysUserPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserPostQueryVo;
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
public interface SysUserPostService extends IService<SysUserPost> {

    /**
     * 添加SysUserJob
     *
     * @param sysUserPostAddParam
     * @return
     */
    boolean saveSysUserPost(SysUserPostAddParam sysUserPostAddParam);

    /**
     * 通过id更新SysUserJob
     *
     * @param sysUserPostUpdateParam
     * @return
     */
    boolean updateSysUserPostById(SysUserPostUpdateParam sysUserPostUpdateParam);

    /**
     * 通过id删除SysUserJob
     *
     * @param idParam
     * @return
     */
    boolean removeSysUserPostById(IdParam idParam);

    /**
     * 通过id查询SysUserJob
     *
     * @param idParam
     * @return
     */
    SysUserPost getSysUserJobById(IdParam idParam);


    /**
     * 查询SysUserJobQueryVo的所有结果
     *
     * @return
     */
    List<SysUserPostQueryVo> listSysUserPostQueryVo();

    /**
     * 查询SysUserPostQueryVo的分页结果
     *
     * @param sysUserPostQueryPageParam
     * @return
     */
    IPage<SysUserPostQueryVo> pageSysUserPostQueryVo(SysUserPostQueryPageParam sysUserPostQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
