package com.jvfast.oss.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.jvfast.oss.config.StorageProperties;
import com.jvfast.oss.service.StorageService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * 阿里云存储
 *
 * @author Walter
 */
@Slf4j
public class ALiYunServiceImpl implements StorageService {

    private StorageProperties storageProperties;
    private OSS client;


    public ALiYunServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String getDomain() {
        return storageProperties.getAliyun().getDomain();
    }

    @Override
    public String getUploadPrefix() {
        return storageProperties.getAliyun().getPrefix();
    }

    @Override
    public String upload(File file, String path) {
        return upload(IoUtil.toStream(file), path);
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    /**
     * 上传到阿里云
     * 举例： upload(stream, "/files/upload/img/20201123/gtesetss.jpg"),将会上传到：
     * /prefix/files/upload/img/20201123/gtesetss.jpg
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return
     */
    @Override
    public String upload(InputStream inputStream, String path) {
        StorageProperties.ALiYun aliyun = this.storageProperties.getAliyun();
        PutObjectResult result = null;
        try {
            // 1. 检查bucket是否存在否则创建
            String bucketName = aliyun.getBucketName();
            String prefix = aliyun.getPrefix();
            // 路径重新配置
            path = path.startsWith("/") ? path.substring(1) : path;
            if (!this.client.doesBucketExist(bucketName)) {
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                this.client.createBucket(createBucketRequest);
                this.client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            // 2. 上传请求
            String key = prefix + "/" + path;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, null);
            result = this.client.putObject(putObjectRequest);
            return aliyun.getDomain() + "/" + key;
        } catch (Exception e) {
            String requestId = result.getRequestId();
            int statusCode = result.getResponse().getStatusCode();
            log.error("阿里云上传文件异常失败,requestId: {}, statusCode: {},异常新型: {}", requestId, statusCode, e);
        }
        return null;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        StorageProperties.ALiYun aliyun = this.storageProperties.getAliyun();
        return upload(data, getFilePath(aliyun.getPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        StorageProperties.ALiYun aliyun = this.storageProperties.getAliyun();
        return upload(inputStream, getFilePath(aliyun.getPrefix(), suffix));
    }

    @Override
    public boolean delete(String url) {
        StorageProperties.ALiYun aliyun = this.storageProperties.getAliyun();
        String domain = aliyun.getDomain();
        String bucketName = aliyun.getBucketName();
        try {
            if (url.contains(domain)) {
                URL parseUrl = URLUtil.url(url);
                String key = parseUrl.getPath().substring(1);
                this.client.deleteObject(bucketName, key);
                return true;
            }
        } catch (Exception oe) {
            log.error("阿里云删除文件异常失败,bucketName: {}, key: {},异常新型: {}", bucketName, url, oe);
        }
        return false;
    }

    @Override
    public boolean restoreObject(String prefix) {
        StorageProperties.ALiYun aliyun = this.storageProperties.getAliyun();
        try {
            String bucketName = aliyun.getBucketName();
            String keyPrefix = prefix.endsWith("/") ? prefix : prefix + "/";
            // 1. 检查bucket是否存在否则创建
            String nextMarker = null;
            ObjectListing objectListing;
            do {
                ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
                listObjectsRequest.setBucketName(bucketName);
                listObjectsRequest.withMaxKeys(100);
                listObjectsRequest.setPrefix(keyPrefix);
                // "/" 为文件夹的分隔符
//                listObjectsRequest.setDelimiter("/");
                if (nextMarker != null) {
                    // 以后的分页，附带nextMarker
                    listObjectsRequest.withMarker(nextMarker);
                }
                objectListing = this.client.listObjects(listObjectsRequest);
                List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
                log.info("准备解冻的文件个数: {}", objectSummaries.size());
                objectSummaries.stream().forEach(oos -> {
                    String objectKey = oos.getKey();
                    log.info("正在解冻的文件: {}", objectKey);
                    restore(bucketName, objectKey);
                });
                log.info("当前迭代解冻文件任务执行完成，执行个数: {}", objectSummaries.size());
                // 下一次分页的nextMarker
                nextMarker = objectListing.getNextMarker();
            } while (objectListing.isTruncated());
            return true;
        } catch (Exception e) {
            log.error("阿里云解冻前缀: {}操作失败，失败信息: {}", prefix, e.getMessage());
        }
        return false;
    }

    private void restore(String bucketName, String key) {
        try {
            ObjectMetadata objectMetadata = this.client.getObjectMetadata(bucketName, key);
            // 校验文件是否为归档文件。
            StorageClass storageClass = objectMetadata.getObjectStorageClass();
            if (storageClass == StorageClass.Archive) {
                // 解冻文件。
                RestoreObjectResult restoreObjectResult = this.client.restoreObject(bucketName, key);
                // 等待解冻完成。
//                do {
//                    Thread.sleep(1000);
//                    objectMetadata = this.client.getObjectMetadata(bucketName, key);
//                } while (!objectMetadata.isRestoreCompleted());
                log.info("解冻文件{}完成，解冻结果状态码: {}(注：202为解冻结束状态码!)", key, restoreObjectResult.getStatusCode());
            }
            // 获取解冻文件。
//            OSSObject ossObject = this.client.getObject(bucketName, key);
//            ossObject.getObjectContent().close();
        } catch (Exception e) {
            log.error("阿里云解冻文件{}异常失败,异常信息: {}", key, e.getMessage());
        }
    }

    @PostConstruct
    private void init() {
        StorageProperties.ALiYun aliyun = this.storageProperties.getAliyun();
        if (!Objects.isNull(aliyun)) {
            this.client = new OSSClientBuilder().build(aliyun.getEndpoint(), aliyun.getAccessKeyId(),
                    aliyun.getAccessKeySecret());
        }
    }
}
