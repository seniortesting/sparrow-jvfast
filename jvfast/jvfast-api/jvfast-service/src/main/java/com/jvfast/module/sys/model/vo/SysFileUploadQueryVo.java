package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件上传表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysFileUploadQueryVo对象", description = "查询文件上传表返回结果对象")
public class SysFileUploadQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    @ApiModelProperty(value = "文件上传者")
    private Long userId;

    @ApiModelProperty(value = "上传文件的token ID,用于分组多个文件")
    private String token;

    @ApiModelProperty(value = "文件访问url")
    private String url;

    @ApiModelProperty(value = "上传文件后的名称")
    private String fileName;

//    @ApiModelProperty(value = "上传文件短路径")
//    private String filePath;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    private LocalDateTime updateTime;
}
