package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysFileUpload;
import com.jvfast.module.sys.model.param.SysFileUploadQueryPageParam;
import com.jvfast.module.sys.model.vo.SysFileUploadQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件上传表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Mapper
public interface SysFileUploadMapper extends BaseMapper<SysFileUpload> {

    /**
     * <p>
     * sys_file_upload分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-18
     */
    List<SysFileUploadQueryVo> getSysFileUploadPage(IPage page, @Param("query") SysFileUploadQueryPageParam queryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
