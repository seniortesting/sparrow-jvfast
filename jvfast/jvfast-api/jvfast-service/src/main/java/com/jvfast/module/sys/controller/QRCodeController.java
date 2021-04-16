package com.jvfast.module.sys.controller;

import com.jvfast.common.api.Result;
import com.jvfast.common.config.captcha.QRCode;
import com.jvfast.module.sys.model.param.QRCodeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "QRCode接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/qrcode")
public class QRCodeController {

    private final QRCode qrCode;

    @PostMapping("base64")
    @ApiOperation(value = "产生二维码(base64)", notes = "生成新的二维码")
    public Result renderBase64(@RequestBody @Valid QRCodeParam qrCodeParam) {
        String base64Image = qrCode.renderBase64(qrCodeParam.getContent());
        String pngImageStr = "data:image/png;base64," + base64Image;
        return Result.success(pngImageStr);
    }

    @PostMapping("img")
    @ApiOperation(value = "产生二维码(PNG图片)", notes = "生成新的二维码")
    public void renderImage(@RequestBody @Valid QRCodeParam qrCodeParam) {
        qrCode.renderImage(qrCodeParam.getContent());
    }
}
