package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-09 11:39
 **/
@Data
public class FileUploadParam {

    @ApiModelProperty(value = "上传用户的token", dataType = "String")
    @NotBlank
    private String token;
    @ApiModelProperty(value = "上传UserId", dataType = "Long")
    @NotNull(message = "上传者ID不能为空")
    private Long userId;
    @ApiModelProperty(value = "上传图片", dataType = "MultipartFile")
    @NotNull
    private MultipartFile file;

}
