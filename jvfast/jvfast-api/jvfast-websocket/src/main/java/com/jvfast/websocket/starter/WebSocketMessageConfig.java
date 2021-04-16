package com.jvfast.websocket.starter;

import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @deprecated  使用其他配置：
 */
//@EnableWebSocketMessageBroker
//@Configuration
public class WebSocketMessageConfig implements WebSocketMessageBrokerConfigurer {

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//         //允许使用socketJs方式访问，访问点为ws，允许跨域
//        // ws://127.0.0.1:2222/ws来和服务器的WebSocket连接
//        registry.addEndpoint("/ws")
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/app");
//        //点对点使用的订阅前缀，不设置的话，默认也是/user/
//        registry.setUserDestinationPrefix("/user");
    //        //基于内存的STOMP消息代理来代替mq的消息代理
//        //订阅Broker名称,/user代表点对点即发指定用户，/topic代表发布广播即群发
//        registry.enableSimpleBroker("/user","/topic");
//    }
}
