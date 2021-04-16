package com.jvfast.module.sys.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysFileUploadConverter;
import com.jvfast.module.sys.mapper.SysFileUploadMapper;
import com.jvfast.module.sys.model.entity.SysFileUpload;
import com.jvfast.module.sys.model.param.SysFileUploadUrlParam;
import com.jvfast.module.sys.model.vo.SysFileUploadQueryVo;
import com.jvfast.module.sys.service.SysFileUploadService;
import com.jvfast.oss.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * <p>
 * 文件上传表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysFileUploadServiceImpl extends ServiceImpl<SysFileUploadMapper, SysFileUpload> implements SysFileUploadService {

    private final JVFastCommonProperties jvFastCommonProperties;
    private final StorageService storageService;
    private static final String DEFAULT_IMAGE_TYPE = "image";
    private static final String DEFAULT_UPLOAD_IMAGE_TYPE = "jpg";

    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public SysFileUploadQueryVo uploadFile(String token, MultipartFile multipartFile, Long userId) throws Exception {
        // 检查参数
        String storePath = jvFastCommonProperties.getStorePath();
        String uploadPrefix = jvFastCommonProperties.getUploadPrefix();
        if (StrUtil.isEmpty(storePath)) {
            throw new BadRequestException("jvfast.store-path参数没有配置");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        MimeType mimeType = MimeTypeUtils.parseMimeType(contentType);
        String subtype = mimeType.getSubtype();
        String type = mimeType.getType();
        String suffix = type.equalsIgnoreCase(DEFAULT_IMAGE_TYPE) ? DEFAULT_UPLOAD_IMAGE_TYPE : subtype;
        String codetoken = StrUtil.isEmpty(token) ? IdUtil.fastSimpleUUID() : token;
        String newFileName = IdUtil.fastSimpleUUID() + "." + suffix;
        // 完整上传文件路径： /files/upload/img/xdsdewewe/t32ewererwewx.png
        byte[] fileBytes = multipartFile.getBytes();
        String storagePath = storePath + uploadPrefix + "/" + type.toLowerCase() + "/" + codetoken + "/" + newFileName;
        String url = storageService.upload(fileBytes, storagePath);
        SysFileUploadQueryVo fileUploadVo = new SysFileUploadQueryVo();
        if (!Objects.isNull(url)) {
            // 保存文件到数据库中
            SysFileUpload sysFileUpload = new SysFileUpload();
            sysFileUpload.setUserId(userId);
            sysFileUpload.setToken(token);
            sysFileUpload.setOriginalName(originalFilename);
            sysFileUpload.setFileName(newFileName);
            sysFileUpload.setFilePath(storagePath);
            sysFileUpload.setUrl(url);
            // 上传文件的标识,如果确认该上传的文件可用就会保留该上传的文件
            sysFileUpload.setStatus(false);
            // 保存文件
            save(sysFileUpload);
            fileUploadVo = SysFileUploadConverter.INSTANCE.convertSysFileUpload(sysFileUpload);
            // 保存到本地
            File parentDirs = FileUtil.mkParentDirs(storagePath);
            if (parentDirs.exists()) {
                FileUtil.writeBytes(fileBytes, storagePath);
            }
        }
        return fileUploadVo;
    }

    @Override
    public boolean removeFileByUrl(SysFileUploadUrlParam sysFileUploadUrlParam) {
        String url = sysFileUploadUrlParam.getUrl();
        LambdaQueryWrapper<SysFileUpload> sysFileUploadLambdaQueryWrapper = Wrappers.<SysFileUpload>lambdaQuery()
                .select(SysFileUpload::getId, SysFileUpload::getFilePath)
                .eq(SysFileUpload::getUrl, url);
        SysFileUpload sysFileUpload = getOne(sysFileUploadLambdaQueryWrapper, false);
        if (!Objects.isNull(sysFileUpload)) {
            Long id = sysFileUpload.getId();
            boolean removeById = removeById(id);
            if (removeById) {
                // 删除oss或者本地文件
                return storageService.delete(url);
            }
        }
        return true;
    }

    @Override
    public boolean removeFileById(IdParam idParam) {
        // 删除保存的文件
        return removeById(idParam.getId());
    }

    @Override
    public boolean removeFilesById(IdBatchParam idBatchParam) {
        AtomicBoolean removedResult = new AtomicBoolean(false);
        List<Long> ids = idBatchParam.getIds();
        ids.forEach(id -> {
            IdParam idParam = new IdParam();
            idParam.setId(id);
            removedResult.set(removeFileById(idParam));
        });
        return removedResult.get();
    }
}
