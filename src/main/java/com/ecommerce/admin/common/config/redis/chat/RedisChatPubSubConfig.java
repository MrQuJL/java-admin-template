package com.ecommerce.admin.common.config.redis.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisChatPubSubConfig {

    public static final String CHAT_ROOM_CHANNEL_PREFIX = "chat:room:";

    @Bean
    public RedisChatSubscriber redisChatSubscriber(ObjectMapper objectMapper, org.springframework.messaging.simp.SimpMessagingTemplate simpMessagingTemplate) {
        return new RedisChatSubscriber(objectMapper, simpMessagingTemplate);
    }

    @Bean
    public RedisMessageListenerContainer chatRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                          RedisChatSubscriber subscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscriber, new PatternTopic(CHAT_ROOM_CHANNEL_PREFIX + "*"));
        return container;
    }
}

