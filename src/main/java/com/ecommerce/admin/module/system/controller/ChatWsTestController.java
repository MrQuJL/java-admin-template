package com.ecommerce.admin.module.system.controller;

import com.ecommerce.admin.common.config.redis.chat.RedisChatMessage;
import com.ecommerce.admin.common.config.redis.chat.RedisChatPubSubConfig;
import com.ecommerce.admin.common.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chat-ws-test")
@Api(tags = "WebSocket聊天室测试接口")
@Validated
@Slf4j
public class ChatWsTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation("发布聊天消息(通过Redis转发到WebSocket订阅者)")
    @PostMapping("/publish")
    public Result<Boolean> publish(@ApiParam(value = "房间ID", required = true, example = "room1")
                                   @RequestParam("roomId") @NotBlank String roomId,
                                   @ApiParam(value = "发送者", required = true, example = "alice")
                                   @RequestParam("from") @NotBlank String from,
                                   @ApiParam(value = "消息内容", required = true, example = "hello")
                                   @RequestParam("content") @NotBlank String content) {
        RedisChatMessage msg = new RedisChatMessage();
        msg.setRoomId(roomId);
        msg.setFrom(from);
        msg.setContent(content);
        msg.setSentAt(LocalDateTime.now());
        try {
            String json = objectMapper.writeValueAsString(msg);
            stringRedisTemplate.convertAndSend(RedisChatPubSubConfig.CHAT_ROOM_CHANNEL_PREFIX + roomId, json);
            return Result.success(Boolean.TRUE);
        } catch (Exception e) {
            log.error("publish chat message failed", e);
            return Result.fail("publish chat message failed: " + e.getMessage());
        }
    }
}

