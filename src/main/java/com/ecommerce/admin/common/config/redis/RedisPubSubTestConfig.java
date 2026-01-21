package com.ecommerce.admin.common.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisPubSubTestConfig {

    @Bean
    public RedisPubSubTestMessageStore redisPubSubTestMessageStore() {
        return new RedisPubSubTestMessageStore();
    }

    @Bean
    public RedisPubSubTestSubscriber redisPubSubTestSubscriber(RedisPubSubTestMessageStore store) {
        return new RedisPubSubTestSubscriber(store);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                      RedisPubSubTestSubscriber subscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscriber, new PatternTopic("test:pubsub:*"));
        return container;
    }
}

