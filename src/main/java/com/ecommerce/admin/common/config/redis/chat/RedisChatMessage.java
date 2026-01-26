package com.ecommerce.admin.common.config.redis.chat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisChatMessage {
    private String roomId;
    private String from;
    private String content;
    private LocalDateTime sentAt;
}

