package com.jvfast.oss.config;

import com.jvfast.oss.validate.AliyunGroup;
import com.jvfast.oss.validate.QCloudGroup;
import com.jvfast.oss.validate.QiniuGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "jvfast.oss")
public class StorageProperties {
    private StorageType type;

    private ALiYun aliyun;
    private QiNiu qiniu;
    private QCloud qcloud;

    @Data
    public static class ALiYun {

        @NotBlank(message = "阿里云EndPoint不能为空", groups = AliyunGroup.class)
        private String endpoint;
        @NotBlank(message = "阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
        private String accessKeyId;
        @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
        private String accessKeySecret;
        @NotBlank(message = "阿里云BucketName不能为空", groups = AliyunGroup.class)
        private String bucketName;
        @NotBlank(message = "阿里云绑定的域名不能为空", groups = AliyunGroup.class)
        @URL(message = "阿里云绑定的域名格式不正确", groups = AliyunGroup.class)
        private String domain;
        private String prefix;
    }

    @Data
    public static class QiNiu {

        @NotBlank(message = "七牛AccessKey不能为空", groups = QiniuGroup.class)
        private String accessKey;
        @NotBlank(message = "七牛SecretKey不能为空", groups = QiniuGroup.class)
        private String secretKey;
        @NotBlank(message = "七牛空间名不能为空", groups = QiniuGroup.class)
        private String bucketName;
        @NotBlank(message = "七牛绑定的域名不能为空", groups = QiniuGroup.class)
        @URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
        private String domain;
        private String prefix;
    }

    @Data
    public static class QCloud {
        //腾讯云AppId
        @NotNull(message = "腾讯云AppId不能为空", groups = QCloudGroup.class)
        private Integer appId;
        //腾讯云SecretId
        @NotBlank(message = "腾讯云SecretId不能为空", groups = QCloudGroup.class)
        private String secretId;
        //腾讯云SecretKey
        @NotBlank(message = "腾讯云SecretKey不能为空", groups = QCloudGroup.class)
        private String secretKey;
        //腾讯云BucketName
        @NotBlank(message = "腾讯云BucketName不能为空", groups = QCloudGroup.class)
        private String bucketName;
        //腾讯云COS所属地区
        @NotBlank(message = "所属地区不能为空", groups = QCloudGroup.class)
        private String region;
        @NotBlank(message = "腾讯云绑定的域名不能为空", groups = QCloudGroup.class)
        @URL(message = "腾讯云绑定的域名格式不正确", groups = QCloudGroup.class)
        private String domain;
        //腾讯云路径前缀
        private String prefix;
    }
}
