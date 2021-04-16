package com.jvfast.common.config.captcha;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class HutoolQRCode implements QRCode {

    private QrConfig qrCodeConfig;

    @Autowired
    private HttpServletResponse response;

    public HutoolQRCode(int width, int height) {
        qrCodeConfig = new QrConfig(width, height);
    }

    @Override
    public String renderBase64(String content) {
        try {
            byte[] generatePng = QrCodeUtil.generatePng(content, qrCodeConfig);
            String encodedByte64 = Base64.encode(generatePng);
            return encodedByte64;
        } catch (Exception e) {
            throw new QRCodeException(e);
        }

    }

    @Override
    public void renderImage(String content) {
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
        response.addHeader(HttpHeaders.CACHE_CONTROL, "post-check=0, pre-check=0");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setContentType("image/jpeg");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            QrCodeUtil.generate(content, qrCodeConfig, "jpg", outputStream);
        } catch (IOException e) {
            throw new QRCodeException(e);
        }
    }

    @Override
    public String decode(String qrcode) {
        try {
            File tempQRCode = FileUtil.createTempFile("qrcode", ".png", null, true);
            File decodeToFile = Base64.decodeToFile(qrcode, tempQRCode);
            String decodeQRCodeImage = QrCodeUtil.decode(decodeToFile);
            return decodeQRCodeImage;
        } catch (Exception e) {
            throw new QRCodeException(e);
        }
    }
}
