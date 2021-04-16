package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通知公告表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysNoticeQueryVo对象", description = "查询通知公告表返回结果对象")
public class SysNoticeQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    @ApiModelProperty(value = "公告标题")
    private String noticeTitle;

    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    private Integer noticeType;

    private String noticeContent;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    private Integer version;

    @ApiModelProperty(value = "对应记录的创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "对应创建记录的人")
    private String createBy;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "对应最后修改记录的人")
    private String updateBy;
}
