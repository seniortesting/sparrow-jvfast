package com.jvfast.websocket.starter;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Message {

    /**
     * 消息来源用户名
     */
    private Long fromId;

    /**
     * 发送者头像
     */
    private String avatar;
    /**
     * 消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
     */
    private String sid;
    /**
     * 消息类型 friend
     */
    private Integer messageType;

    @NotBlank(message = "消息内容不能为空")
    private String content;
    /**
     * 服务端时间戳毫秒数
     */
    private long timestamp;
}
