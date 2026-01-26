package com.ecommerce.admin.common.config.redis.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
public class RedisChatSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RedisChatSubscriber(ObjectMapper objectMapper, SimpMessagingTemplate simpMessagingTemplate) {
        this.objectMapper = objectMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            RedisChatMessage chatMessage = objectMapper.readValue(body, RedisChatMessage.class);
            String roomId = extractRoomId(channel, chatMessage.getRoomId());
            if (roomId == null) {
                return;
            }
            chatMessage.setRoomId(roomId);
            simpMessagingTemplate.convertAndSend("/topic/chat/room/" + roomId, chatMessage);
        } catch (Exception e) {
            log.warn("Redis chat pubsub message parse failed, channel={}, body={}", channel, body, e);
        }
    }

    private String extractRoomId(String channel, String roomIdInBody) {
        if (channel != null && channel.startsWith(RedisChatPubSubConfig.CHAT_ROOM_CHANNEL_PREFIX)) {
            return channel.substring(RedisChatPubSubConfig.CHAT_ROOM_CHANNEL_PREFIX.length());
        }
        return roomIdInBody;
    }
}

