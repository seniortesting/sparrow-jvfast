package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysFileUpload;
import com.jvfast.module.sys.model.param.SysFileUploadUrlParam;
import com.jvfast.module.sys.model.vo.SysFileUploadQueryVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件上传表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
public interface SysFileUploadService extends IService<SysFileUpload> {
    /***************************** 以下为扩展接口 ******************************************************/
    SysFileUploadQueryVo uploadFile(String token, MultipartFile multipartFile, Long userId) throws Exception;

    boolean removeFileByUrl(SysFileUploadUrlParam sysFileUploadUrlParam);

    boolean removeFileById(IdParam idParam);

    boolean removeFilesById(IdBatchParam idParam);
}
