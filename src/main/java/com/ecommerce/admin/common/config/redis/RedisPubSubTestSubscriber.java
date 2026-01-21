package com.ecommerce.admin.common.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

public class RedisPubSubTestSubscriber implements MessageListener {

    private final RedisPubSubTestMessageStore store;

    public RedisPubSubTestSubscriber(RedisPubSubTestMessageStore store) {
        this.store = store;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        store.add(channel, body);
    }
}

