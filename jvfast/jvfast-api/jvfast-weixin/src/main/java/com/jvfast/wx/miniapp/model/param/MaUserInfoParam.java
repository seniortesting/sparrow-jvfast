package com.jvfast.wx.miniapp.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 获取用户的解密后的信息
 *
 * @author Walter
 */
@Data
public class MaUserInfoParam {

    private String userId;
    @NotBlank(message = "sessionKey不能为空")
    private String sessionKey;
    @NotBlank(message = "signature不能为空")
    private String signature;
    @NotBlank(message = "rawData不能为空")
    private String rawData;
    @NotBlank(message = "encryptedData不能为空")
    private String encryptedData;
    @NotBlank(message = "iv不能为空")
    private String iv;
}
