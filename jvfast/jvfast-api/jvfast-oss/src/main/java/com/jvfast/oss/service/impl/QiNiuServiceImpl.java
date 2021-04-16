package com.jvfast.oss.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.jvfast.oss.config.StorageProperties;
import com.jvfast.oss.service.StorageService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * 七牛云存储
 *
 * @author Walter
 */
@Slf4j
public class QiNiuServiceImpl implements StorageService {

    private StorageProperties storageProperties;
    private UploadManager uploadManager;
    private BucketManager bucketManager;
    private Auth auth;

    public QiNiuServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String getDomain() {
        return storageProperties.getQiniu().getDomain();
    }

    @Override
    public String getUploadPrefix() {
        return storageProperties.getQiniu().getPrefix();
    }

    @Override
    public String upload(File file, String path) {
        return upload(FileUtil.readBytes(file), path);
    }

    /**
     * 上传到七牛云
     * 举例： upload(stream, "/files/upload/img/20201123/gtesetss.jpg"),将会上传到：
     * /prefix/files/upload/img/20201123/gtesetss.jpg
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return
     */
    @Override
    public String upload(byte[] data, String path) {
        StorageProperties.QiNiu qiniu = this.storageProperties.getQiniu();
        try {
            String bucketName = qiniu.getBucketName();
            String prefix = qiniu.getPrefix();
            // 对路径进行重新设置
            path = path.startsWith("/") ? path.substring(1) : path;
            String key = prefix + "/" + path;
            String token = this.auth.uploadToken(bucketName);
            Response res = this.uploadManager.put(data, key, token);
            if (!res.isOK()) {
                log.error("上传七牛云出错：{}" + res.toString());
                return null;
            }
            return qiniu.getDomain() + "/" + key;
        } catch (Exception e) {
            log.error("上传文件失败，请核对七牛配置信息: {}", e);
        }
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return upload(data, path);
        } catch (IOException e) {
            log.error("上传七牛云失败: {}", e);
        }
        return null;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        StorageProperties.QiNiu qiniu = this.storageProperties.getQiniu();
        return upload(data, getFilePath(qiniu.getPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        StorageProperties.QiNiu qiniu = this.storageProperties.getQiniu();
        return upload(inputStream, getFilePath(qiniu.getPrefix(), suffix));
    }

    @Override
    public boolean delete(String url) {
        StorageProperties.QiNiu qiniu = this.storageProperties.getQiniu();
        String bucketName = qiniu.getBucketName();
        String domain = qiniu.getDomain();
        try {
            if (url.contains(domain)) {
                URL parseUrl = URLUtil.url(url);
                String key = parseUrl.getPath().substring(1);
                Response response = this.bucketManager.delete(bucketName, key);
                return response.isOK();
            }
        } catch (Exception e) {
            log.error("七牛云删除文件异常失败,bucketName: {}, key: {},异常新型: {}", bucketName, url, e);
        }
        return false;
    }

    @Override
    public boolean restoreObject(String objectName) {
        return false;
    }

    @PostConstruct
    private void init() {
        StorageProperties.QiNiu qiniu = this.storageProperties.getQiniu();
        if (!Objects.isNull(qiniu)) {
            Configuration configuration = new Configuration(Zone.autoZone());
            this.uploadManager = new UploadManager(configuration);
            this.auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
            this.bucketManager = new BucketManager(this.auth, configuration);
        }
    }
}
