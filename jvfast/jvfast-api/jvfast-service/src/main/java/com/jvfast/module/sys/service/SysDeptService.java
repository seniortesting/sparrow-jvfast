package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysDept;
import com.jvfast.module.sys.model.param.SysDeptAddParam;
import com.jvfast.module.sys.model.param.SysDeptQueryPageParam;
import com.jvfast.module.sys.model.param.SysDeptUpdateParam;
import com.jvfast.module.sys.model.vo.SysDeptQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 添加SysDept
     *
     * @param sysDept
     * @return
     */
    boolean saveSysDept(SysDeptAddParam sysDept);

    /**
     * 通过id更新SysDept
     *
     * @param sysDept
     * @return
     */
    boolean updateSysDeptById(SysDeptUpdateParam sysDept);

    /**
     * 通过id删除SysDept
     *
     * @param idParam
     * @return
     */
    boolean removeSysDeptById(IdParam idParam);

    boolean removeSysDeptsById(IdBatchParam idBatchParam);

    /**
     * 通过id查询SysDept
     *
     * @param idParam
     * @return
     */
    SysDept getSysDeptById(IdParam idParam);


    /**
     * 查询SysDeptQueryVo的所有结果
     *
     * @return
     */
    List<SysDeptQueryVo> listSysDeptQueryVo();

    /**
     * 查询SysDeptQueryVo的分页结果
     *
     * @param sysDeptQueryPageParam
     * @return
     */
    IPage<SysDeptQueryVo> pageSysDeptQueryVo(SysDeptQueryPageParam sysDeptQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    List<SysDeptQueryVo> getDeptTreeList();
}
