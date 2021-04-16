package com.jvfast.oss.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jvfast.oss.config.StorageProperties;
import com.jvfast.oss.service.StorageService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qiniu.util.IOUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * 腾讯云存储
 *
 * @author Walter
 */
@Slf4j
public class QCloudServiceImpl implements StorageService {
    private StorageProperties storageProperties;
    private COSClient client;


    public QCloudServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String getDomain() {
        return storageProperties.getQcloud().getDomain();
    }

    @Override
    public String getUploadPrefix() {
        return storageProperties.getQcloud().getPrefix();
    }

    @Override
    public String upload(File file, String path) {
        return upload(FileUtil.readBytes(file), path);
    }

    /**
     * 上传到腾讯云
     * 举例：
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return
     */
    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        path = path.startsWith("/") ? path : "/" + path;
        //上传到腾讯云
        StorageProperties.QCloud qcloud = this.storageProperties.getQcloud();
        String bucketName = qcloud.getBucketName();
        String prefix = qcloud.getPrefix();
        String cosPath = "/" + prefix + path;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cosPath, new ByteArrayInputStream(data), null);
        PutObjectResult response = this.client.putObject(putObjectRequest);

        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (jsonObject.getInt("code") != 0) {
            log.error("文件上传失败，错误信息: {}", jsonObject.getStr("message"));
            return null;
        }
        return qcloud.getDomain() + cosPath;
    }


    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return upload(data, path);
        } catch (IOException e) {
            log.error("腾讯云上传文件失败: {}", e);
        }
        return null;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        StorageProperties.QCloud qcloud = this.storageProperties.getQcloud();
        return upload(data, getFilePath(qcloud.getPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        StorageProperties.QCloud qcloud = this.storageProperties.getQcloud();
        return upload(inputStream, getFilePath(qcloud.getPrefix(), suffix));
    }

    @Override
    public boolean delete(String url) {
        StorageProperties.QCloud qcloud = this.storageProperties.getQcloud();
        String bucketName = qcloud.getBucketName();
        String domain = qcloud.getDomain();
        try {
            if (url.contains(domain)) {
                URL parseUrl = URLUtil.url(url);
                String corPath = parseUrl.getPath();
                DeleteObjectRequest delFileRequest = new DeleteObjectRequest(bucketName, corPath);
                this.client.deleteObject(delFileRequest);
                return true;
            }
        } catch (Exception e) {
            log.error("腾讯云删除文件异常失败,bucketName: {}, key: {},异常新型: {}", bucketName, url, e);
        }
        return false;
    }

    @Override
    public boolean restoreObject(String objectName) {
        return false;
    }

    @PostConstruct
    private void init() {
        StorageProperties.QCloud qcloud = this.storageProperties.getQcloud();
        if (!Objects.isNull(qcloud)) {
            BasicCOSCredentials credentials = new BasicCOSCredentials(qcloud.getSecretId(), qcloud.getSecretKey());
            //初始化客户端配置
            ClientConfig clientConfig = new ClientConfig();
            //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
            clientConfig.setRegion(new Region(qcloud.getRegion()));
            this.client = new COSClient(credentials, clientConfig);
        }
    }
}
