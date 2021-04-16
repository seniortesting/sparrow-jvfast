package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysCity;
import com.jvfast.module.sys.model.param.SysCityAddParam;
import com.jvfast.module.sys.model.param.SysCityQueryPageParam;
import com.jvfast.module.sys.model.param.SysCityUpdateParam;
import com.jvfast.module.sys.model.vo.SysCityQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-02-07
 */
public interface SysCityService extends IService<SysCity> {


    /**
     * 添加SysCity
     *
     * @param sysCity
     * @return
     */
    boolean saveSysCity(SysCityAddParam sysCity);

    /**
     * 通过id更新SysCity
     *
     * @param sysCity
     * @return
     */
    boolean updateSysCityById(SysCityUpdateParam sysCity);

    /**
     * 通过id删除SysCity
     *
     * @param idParam
     * @return
     */
    boolean removeSysCityById(IdParam idParam);

    /**
     * 通过id批量删除SysCity
     *
     * @param idBatchParam
     * @return
     */
    boolean removeSysCityByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询SysCity
     *
     * @param idParam
     * @return
     */
    SysCity getSysCityById(IdParam idParam);

    /**
     * 查询SysCityQueryVo的所有结果
     *
     * @return
     */
    List<SysCityQueryVo> listSysCityQueryVo(String pid);

    /**
     * 查询SysCityQueryVo的分页结果
     *
     * @param sysCityQueryPageParam
     * @return
     */
    IPage<SysCityQueryVo> pageSysCityQueryVo(SysCityQueryPageParam sysCityQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/

}

