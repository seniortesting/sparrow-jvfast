package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysCompany;
import com.jvfast.module.sys.model.param.SysCompanyAddParam;
import com.jvfast.module.sys.model.param.SysCompanyUpdateParam;
import com.jvfast.module.sys.model.vo.SysCompanyQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * CvrCompany对象转换接口
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface SysCompanyConverter {

    SysCompanyConverter INSTANCE = Mappers.getMapper(SysCompanyConverter.class);

    /**
    *  添加操作
    *  CvrCompanyAddParam对象转换为CvrCompany
    * @param cvrCompanyAddParam
    * @return
    */
    SysCompany convertCvrCompanyAddParam(SysCompanyAddParam cvrCompanyAddParam);
    /**
     *  修改操作
     *  CvrCompanyUpdateParam对象转换为CvrCompany
     * @param sysCompanyUpdateParam
     * @return
     */
    SysCompany convertCvrCompanyUpdateParam(SysCompanyUpdateParam sysCompanyUpdateParam);

    /**
     *  查询列表转化实体
     *  CvrCompany对象转换为CvrCompanyQueryVo
     * @param sysCompany
     * @return
     */
    List<SysCompanyQueryVo> convertCvrCompanyList(List<SysCompany> sysCompany);
     /**
     *  查询分页操作
     *  CvrCompany对象转换为CvrCompanyQueryVo
     * @param sysCompany
     * @return
     */
    SysCompanyQueryVo convertCvrCompany(SysCompany sysCompany);


}
