package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysDictData;
import com.jvfast.module.sys.model.param.SysDictDataAddParam;
import com.jvfast.module.sys.model.param.SysDictDataQueryPageParam;
import com.jvfast.module.sys.model.param.SysDictDataUpdateParam;
import com.jvfast.module.sys.model.vo.SysDictDataQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 添加SysDict
     *
     * @param sysDict
     * @return
     */
    boolean saveSysDictData(SysDictDataAddParam sysDict);

    /**
     * 通过id更新SysDict
     *
     * @param sysDict
     * @return
     */
    boolean updateSysDictDataById(SysDictDataUpdateParam sysDict);

    /**
     * 通过id删除SysDict
     *
     * @param idParam
     * @return
     */
    boolean removeSysDictDataById(IdParam idParam);

    boolean removeSysDictDatasById(IdBatchParam idParam);

    /**
     * 通过id查询SysDict
     *
     * @param idParam
     * @return
     */
    SysDictData getSysDictDataById(IdParam idParam);


    /**
     * 查询SysDictQueryVo的所有结果
     *
     * @return
     */
    List<SysDictDataQueryVo> listSysDictDataQueryVo();

    /**
     * 查询SysDictQueryVo的分页结果
     *
     * @param sysDictQueryPageParam
     * @return
     */
    IPage<SysDictDataQueryVo> pageSysDictDataQueryVo(SysDictDataQueryPageParam sysDictQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
