package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysFileUpload;
import com.jvfast.module.sys.model.vo.SysFileUploadQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysFileUpload对象转换接口
 */
@Mapper
public interface SysFileUploadConverter {

    SysFileUploadConverter INSTANCE = Mappers.getMapper(SysFileUploadConverter.class);

    /**
     * 查询分页操作
     * SysFileUpload对象转换为SysFileUploadQueryVo
     *
     * @param sysFileUpload
     * @return
     */
    SysFileUploadQueryVo convertSysFileUpload(SysFileUpload sysFileUpload);

    List<SysFileUploadQueryVo> convertSysFileUploadList(List<SysFileUpload> sysFileUploadList);
}
