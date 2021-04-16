package com.jvfast.oss.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

public interface StorageService {

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    default String getFilePath(String prefix, String suffix) {
        //生成uuid
        String uuid = IdUtil.fastUUID();
        //文件路径
        String path = DateUtil.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if (StrUtil.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }

    String getDomain();
    String getUploadPrefix();
    /**
     * 文件上传
     *
     * @param file 文件
     * @param path 文件路径
     * @return 返回http路径
     */
    String upload(File file, String path);

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @return 返回http地址
     */
    String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @return 返回http地址
     */
    String uploadSuffix(InputStream inputStream, String suffix);

    /**
     * 删除资源
     * @param url
     * @return
     */
    boolean delete(String url);

    /**
     * 解冻资源
     * @param objectName
     * @return
     */
    boolean restoreObject(String objectName);
}
