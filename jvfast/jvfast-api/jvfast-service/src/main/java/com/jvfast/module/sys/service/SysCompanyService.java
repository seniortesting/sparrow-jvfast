package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysCompany;
import com.jvfast.module.sys.model.param.SysCompanyAddParam;
import com.jvfast.module.sys.model.param.SysCompanyQueryPageParam;
import com.jvfast.module.sys.model.param.SysCompanyUpdateParam;
import com.jvfast.module.sys.model.vo.SysCompanyQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;
/**
 * <p>
 * 公司表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-19
 */
public interface SysCompanyService extends IService<SysCompany> {

        /**
     * 添加CvrCompany
     * @param cvrCompany
     * @return
     */
    boolean saveCvrCompany(SysCompanyAddParam cvrCompany);
    /**
     * 通过id更新CvrCompany
     * @param cvrCompany
     * @return
     */
    boolean updateCvrCompanyById(SysCompanyUpdateParam cvrCompany);
    /**
     * 通过id删除CvrCompany
     * @param idParam
     * @return
     */
    boolean removeCvrCompanyById(IdParam idParam);
    /**
     * 通过id批量删除CvrCompany
     * @param idBatchParam
     * @return
     */
    boolean removeCvrCompanyByIds(IdBatchParam idBatchParam);
    /**
     * 通过id查询CvrCompany
     * @param idParam
     * @return
     */
    SysCompany getCvrCompanyById(IdParam idParam);
    /**
     * 查询CvrCompanyQueryVo的所有结果
     * @return
     */
    List<SysCompanyQueryVo> listCvrCompanyQueryVo();
    /**
     * 查询CvrCompanyQueryVo的分页结果
     * @param sysCompanyQueryPageParam
     * @return
     */
    IPage<SysCompanyQueryVo> pageCvrCompanyQueryVo(SysCompanyQueryPageParam sysCompanyQueryPageParam) throws DataAccessException;
    /***************************** 以下为扩展接口 ******************************************************/
}
