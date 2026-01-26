package com.ecommerce.admin.common.config.redis.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class RedisPubSubTestMessageStore {

    /**
     * 每个频道最多存储的消息数量，超过该数量会自动删除最早的消息
     */
    private static final int MAX_PER_CHANNEL = 200;

    private final Map<String, Deque<PubSubMessage>> messagesByChannel = new ConcurrentHashMap<>();

    /**
     * 添加一条消息到指定频道的队列中
     * @param channel 频道名
     * @param message 消息内容
     */
    public void add(String channel, String message) {
        Deque<PubSubMessage> deque = messagesByChannel.computeIfAbsent(channel, key -> new ConcurrentLinkedDeque<>());
        deque.addLast(new PubSubMessage(channel, message, LocalDateTime.now()));
        while (deque.size() > MAX_PER_CHANNEL) {
            deque.pollFirst();
        }
    }

    /**
     * 从队列中获取最近的limit条消息
     * @param channel 频道名
     * @param limit 要获取的消息数量，不能为负数
     * @return 最近的limit条消息，按接收时间升序排序
     */
    public List<PubSubMessage> list(String channel, int limit) {
        Deque<PubSubMessage> deque = messagesByChannel.get(channel);
        if (deque == null || deque.isEmpty()) {
            return Collections.emptyList();
        }
        // 防御式编程，防止limit为负数
        int size = Math.max(0, limit);
        // 预分配空间，避免后续添加时扩容
        List<PubSubMessage> result = new ArrayList<>(Math.min(size, deque.size()));
        // 跳过前面的消息，只保留最近的size条
        // 例如：deque.size() = 100, limit = 20, 则跳过前面的80条消息
        int skipped = Math.max(0, deque.size() - size);
        int index = 0;
        // 遍历队列，跳过前面的消息，只保留最近的size条
        for (PubSubMessage msg : deque) {
            if (index++ < skipped) {
                continue;
            }
            result.add(msg);
        }
        return result;
    }

    /**
     * 获取所有频道名
     * @return 所有频道名，按字典序排序
     */
    public List<String> channels() {
        List<String> list = new ArrayList<>(messagesByChannel.keySet());
        Collections.sort(list);
        return list;
    }

    /**
     * 消息类，用于存储发布订阅消息
     */
    public static class PubSubMessage {
        private final String channel;
        private final String message;
        private final LocalDateTime receivedAt;

        public PubSubMessage(String channel, String message, LocalDateTime receivedAt) {
            this.channel = channel;
            this.message = message;
            this.receivedAt = receivedAt;
        }

        public String getChannel() {
            return channel;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getReceivedAt() {
            return receivedAt;
        }
    }
}

