package com.jvfast.wx.mp.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.jvfast.common.config.mybatis.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 文章抽象：帮助中心文章、公告、资讯文章等分别存储到不同的表
 */
@Data
@TableName("wx_article")
public class WxMPArticle extends BaseEntity {
	@TableId(type = IdType.ID_WORKER)
	private int id;
	private int type;
	@TableField(insertStrategy= FieldStrategy.IGNORED)//title重复则不插入
	@NotEmpty(message = "标题不得为空")
	private String title;
	private String tags;
	private String summary;
	private String content;
	private String category;
	private String subCategory;
	private Date startTime;
	private Date endTime;
	private int openCount;
	private String targetLink;
	private String image;

}
