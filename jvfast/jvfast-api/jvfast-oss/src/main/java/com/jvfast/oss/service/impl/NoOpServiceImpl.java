package com.jvfast.oss.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.util.ServletUtil;
import com.jvfast.oss.service.StorageService;
import com.qiniu.util.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
public class NoOpServiceImpl implements StorageService {

    private JVFastCommonProperties jvFastCommonProperties;

    public NoOpServiceImpl(JVFastCommonProperties jvFastCommonProperties) {
        this.jvFastCommonProperties = jvFastCommonProperties;
    }

    @Override
    public String getDomain() {
        return "";
    }

    @Override
    public String getUploadPrefix() {
        return "";
    }

    @Override
    public String upload(File file, String path) {
        return upload(FileUtil.readBytes(file), path);
    }

    /**
     * 直接保存文件到本地：
     * 保存的路径必须是: /files/upload/img/20201130/we434x3.png, 因为files文件夹会做url pattern
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return
     */
    @Override
    public String upload(byte[] data, String path) {
        path = path.startsWith("/") ? path : "/" + path;
        try {
            //创建目录
            String domain = ServletUtil.getDomain();
            File parentDirs = FileUtil.mkParentDirs(path);
            if (parentDirs.exists()) {
                FileUtil.writeBytes(data, path);
                return domain + path;
            }
        } catch (Exception e) {
            log.error("保存文件异常失败,path: {},异常新型: {}", path, e);
        }
        return null;
    }


    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return upload(data, path);
        } catch (IOException e) {
            log.error("保存文件失败: {}", e);
        }
        return null;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        String uploadPrefix = this.jvFastCommonProperties.getUploadPrefix();
        return upload(data, getFilePath(uploadPrefix, suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        String uploadPrefix = this.jvFastCommonProperties.getUploadPrefix();
        return upload(inputStream, getFilePath(uploadPrefix, suffix));
    }

    /**
     * 删除文件，路径例如： https://pingbook.top/files/upload/test/xcsds/4w4434.png
     *
     * @param url
     * @return
     */
    @Override
    public boolean delete(String url) {
        try {
            String domain = ServletUtil.getDomain();
            if (url.contains(domain)) {
                URL parseUrl = URLUtil.url(url);
                String filePath = parseUrl.getPath();
                File file = FileUtil.file(filePath);
                if (file.exists()) {
                    return FileUtil.del(file);
                }
            }
        } catch (Exception e) {
            log.error("删除本地文件失败,文件路径: {},异常信息: {}", url, e);
        }
        return false;
    }

    @Override
    public boolean restoreObject(String objectName) {
        return false;
    }
}
