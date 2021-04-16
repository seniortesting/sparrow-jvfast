package com.jvfast.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "jvfast")
public class JVFastCommonProperties {

    /**
     * 演示模式
     */
    private Boolean demoEnabled = false;
    /**
     * 上传下载的文件路径
     */
    private String storePath = "/files";

    private String resourcePatterns = "/files";
    /*
     * swagger 配置
     */
    private SWagger swagger;
    /**
     * 图形验证码
     */
    private Captcha captcha;

    private QRCode qrcode;
    /**
     * 加密服务,采用AES加密发送的请求和返回的要请求结果
     */
    private Encrypt encrypt;

    /**
     * 上传路径
     *
     * @return
     */
    public String getUploadPrefix() {
        return "upload";
    }

    /**
     * 下载路径
     *
     * @return
     */
    public String getDownloadPrefix() {
        return "download";
    }

    @Data
    public static class SWagger {

        /**
         * 标题
         **/
        private String title;

        private String version;
        /**
         * 描述
         **/
        private String description;
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";
        /**
         * 联系人信息
         */
        private Contact contact = new Contact();

        /**
         * 测试用token
         */
        private String testToken;

        @Data
        @NoArgsConstructor
        public static class Contact {

            /**
             * 联系人
             **/
            private String name = "Walter Hu";
            /**
             * 联系人url
             **/
            private String url = "";
            /**
             * 联系人email
             **/
            private String email = "alterhu2020@gmail.com";

        }
    }

    @Data
    public static class Captcha {

        private Boolean enabled;
        /**
         * 过期时间,15分钟
         */
        private Integer expiredSeconds = 900;
        /**
         * 宽度
         */
        private Integer width = 200;
        /**
         * 高度
         */
        private Integer height = 50;

    }

    @Data
    public static class QRCode {
        /**
         * 宽度
         */
        private Integer width = 300;
        /**
         * 高度
         */
        private Integer height = 300;
    }

    @Data
    public static class Encrypt {


        private Boolean enabled;
        /**
         * aes 密钥长度不得小于16
         */
        private String secretKey;
    }

}
