package com.ecommerce.admin.module.system.controller;

import com.ecommerce.admin.common.config.redis.RedisPubSubTestMessageStore;
import com.ecommerce.admin.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/redis-pubsub-test")
@Api(tags = "Redis发布订阅测试接口")
@Validated
@Slf4j
public class RedisPubSubTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisPubSubTestMessageStore messageStore;

    @ApiOperation("发布消息到指定频道(test:pubsub:*)")
    @PostMapping("/publish")
    public Result<Boolean> publish(@ApiParam(value = "频道后缀", required = true, example = "room1")
                                   @RequestParam("channel") @NotBlank String channelSuffix,
                                   @ApiParam(value = "消息内容", required = true, example = "hello")
                                   @RequestParam("message") @NotBlank String message) {
        String channel = "test:pubsub:" + channelSuffix;
        stringRedisTemplate.convertAndSend(channel, message);
        return Result.success(Boolean.TRUE);
    }

    @ApiOperation("查看当前应用节点收到的消息")
    @GetMapping("/messages")
    public Result<List<RedisPubSubTestMessageStore.PubSubMessage>> messages(
            @ApiParam(value = "频道后缀", required = true, example = "room1")
            @RequestParam("channel") @NotBlank String channelSuffix,
            @ApiParam(value = "返回条数(1-200)", example = "50")
            @RequestParam(value = "limit", defaultValue = "50") @Min(1) @Max(200) Integer limit) {
        String channel = "test:pubsub:" + channelSuffix;
        return Result.success(messageStore.list(channel, limit));
    }

    @ApiOperation("查看当前应用节点已记录的频道列表")
    @GetMapping("/channels")
    public Result<List<String>> channels() {
        return Result.success(messageStore.channels());
    }
}

