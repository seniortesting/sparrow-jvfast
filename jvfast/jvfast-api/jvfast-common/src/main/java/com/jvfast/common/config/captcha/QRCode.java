package com.jvfast.common.config.captcha;

public interface QRCode {

    /**
     * 产生二维码的base64图片
     *
     * @return
     */
    String renderBase64(String content);

    /**
     * 直接产生对应的二维码图片
     *
     * @param content 返回图片
     */
    void renderImage(String content);

    /**
     * 解析二维码内容
     *
     * @param qrcode 二维码图片的base64字符串
     * @return 返回识别的二维码内容
     */
    String decode(String qrcode);
}
