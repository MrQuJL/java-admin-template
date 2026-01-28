package com.ecommerce.admin.common.config.websocket;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * STOMP 通道拦截器
 * 用于在建立 STOMP 连接时进行身份验证
 */
@Slf4j
@Component
public class StompChannelInterceptor implements ChannelInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        // 1. 仅拦截 CONNECT 命令
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 2. 获取 Authorization 头
            List<String> authorization = accessor.getNativeHeader(AUTHORIZATION_HEADER);
            
            if (authorization == null || authorization.isEmpty()) {
                log.error("WebSocket 连接失败: 未提供 Authorization 头");
                throw new MessagingException("未提供 Authorization 头");
            }

            String token = authorization.get(0);
            
            // 3. 验证 Token (去除 Bearer 前缀)
            if (token.startsWith(TOKEN_PREFIX)) {
                token = token.substring(TOKEN_PREFIX.length());
            }

            // 4. 执行实际的验证逻辑
            // 使用 Sa-Token 验证 Token
            Object loginId = StpUtil.getLoginIdByToken(token);

            if (loginId == null) {
                log.error("WebSocket 连接失败: 无效的 Token -> {}", token);
                throw new MessagingException("无效的 Token");
            }

            // 5. 验证通过，可以在 accessor.getSessionAttributes() 中存储用户信息
            log.info("WebSocket 连接成功: User verified with token {}", token);
        }
        
        return message;
    }
}
