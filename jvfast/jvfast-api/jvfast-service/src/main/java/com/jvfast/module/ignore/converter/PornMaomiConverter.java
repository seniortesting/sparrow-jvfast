package com.jvfast.module.ignore.converter;

import com.jvfast.module.ignore.model.entity.PornMaomi;
import com.jvfast.module.ignore.model.param.PornMaomiAddParam;
import com.jvfast.module.ignore.model.param.PornMaomiUpdateParam;
import com.jvfast.module.ignore.model.vo.PornMaomiQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PornMaomi对象转换接口
 * @author Walter Hu
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface PornMaomiConverter {

    PornMaomiConverter INSTANCE = Mappers.getMapper(PornMaomiConverter.class);

    /**
     *  添加操作
     *  <p>
     *  PornMaomiAddParam对象转换为PornMaomi
     *
     * @param pornMaomiAddParam
     * @return
     */
    PornMaomi convertPornMaomiAddParam(PornMaomiAddParam pornMaomiAddParam);

    /**
     *  修改操作
     *  <p>
     *  PornMaomiUpdateParam对象转换为PornMaomi
     *
     * @param pornMaomiUpdateParam
     * @return
     */
    PornMaomi convertPornMaomiUpdateParam(PornMaomiUpdateParam pornMaomiUpdateParam);

    /**
     *  查询列表转化实体
     *  <p>
     *  PornMaomi对象转换为PornMaomiQueryVo
     *
     * @param pornMaomi
     * @return
     */
    List<PornMaomiQueryVo> convertPornMaomiList(List<PornMaomi> pornMaomi);

    /**
     *  查询分页操作
     *  <p>
     *  PornMaomi对象转换为PornMaomiQueryVo
     *
     * @param pornMaomi
     * @return
     */
    PornMaomiQueryVo convertPornMaomi(PornMaomi pornMaomi);

}
