package com.ecommerce.admin.module.system.controller;

import com.ecommerce.admin.common.config.redis.chat.RedisChatMessage;
import com.ecommerce.admin.common.config.redis.chat.RedisChatPubSubConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Controller
@Slf4j
public class ChatWsTestStompController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MessageMapping("/chat/room/{roomId}")
    public void send(@DestinationVariable("roomId") String roomId, ChatSendRequest request) {
        RedisChatMessage msg = new RedisChatMessage();
        msg.setRoomId(roomId);
        msg.setFrom(request.getFrom());
        msg.setContent(request.getContent());
        msg.setSentAt(LocalDateTime.now());
        try {
            String json = objectMapper.writeValueAsString(msg);
            stringRedisTemplate.convertAndSend(RedisChatPubSubConfig.CHAT_ROOM_CHANNEL_PREFIX + roomId, json);
        } catch (Exception e) {
            log.error("stomp send failed", e);
        }
    }

    @Data
    public static class ChatSendRequest {
        @NotBlank
        private String from;
        @NotBlank
        private String content;
    }
}

