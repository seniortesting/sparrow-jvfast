package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysDictType;
import com.jvfast.module.sys.model.param.SysDictTypeAddParam;
import com.jvfast.module.sys.model.param.SysDictTypeQueryPageParam;
import com.jvfast.module.sys.model.param.SysDictTypeUpdateParam;
import com.jvfast.module.sys.model.vo.SysDictTypeQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统字典类型表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 添加SysDictType
     *
     * @param sysDictType
     * @return
     */
    boolean saveSysDictType(SysDictTypeAddParam sysDictType);

    /**
     * 通过id更新SysDictType
     *
     * @param sysDictType
     * @return
     */
    boolean updateSysDictTypeById(SysDictTypeUpdateParam sysDictType);

    /**
     * 通过id删除SysDictType
     *
     * @param idParam
     * @return
     */
    boolean removeSysDictTypeById(IdParam idParam);

    boolean removeSysDictTypesById(IdBatchParam idParam);

    /**
     * 通过id查询SysDictType
     *
     * @param idParam
     * @return
     */
    SysDictType getSysDictTypeById(IdParam idParam);


    /**
     * 查询SysDictTypeQueryVo的所有结果
     *
     * @return
     */
    List<SysDictTypeQueryVo> listSysDictTypeQueryVo();

    /**
     * 查询SysDictTypeQueryVo的分页结果
     *
     * @param sysDictTypeQueryPageParam
     * @return
     */
    IPage<SysDictTypeQueryVo> pageSysDictTypeQueryVo(SysDictTypeQueryPageParam sysDictTypeQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
