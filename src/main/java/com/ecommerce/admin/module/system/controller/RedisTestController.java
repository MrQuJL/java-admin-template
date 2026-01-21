package com.ecommerce.admin.module.system.controller;

import com.ecommerce.admin.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/redis-test")
@Api(tags = "Redis测试接口")
@Validated
@Slf4j
public class RedisTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("Redis连通性测试")
    @GetMapping("/ping")
    public Result<String> ping() {
        String key = "system:redis:test:ping";
        String value = "pong@" + LocalDateTime.now();
        stringRedisTemplate.opsForValue().set(key, value, 60, TimeUnit.SECONDS);
        String readBack = stringRedisTemplate.opsForValue().get(key);
        return Result.success(readBack);
    }

    @ApiOperation("写入字符串KV")
    @PostMapping("/set")
    public Result<Boolean> set(@ApiParam(value = "Key", required = true, example = "system:redis:test:k1")
                               @RequestParam @NotBlank String key,
                               @ApiParam(value = "Value", required = true, example = "v1")
                               @RequestParam @NotBlank String value,
                               @ApiParam(value = "TTL(秒)，不传表示不过期", example = "60")
                               @RequestParam(required = false) @Min(1) Long ttlSeconds) {
        if (ttlSeconds == null) {
            stringRedisTemplate.opsForValue().set(key, value);
        } else {
            stringRedisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
        }
        return Result.success(Boolean.TRUE);
    }

    @ApiOperation("读取字符串KV")
    @GetMapping("/get")
    public Result<String> get(@ApiParam(value = "Key", required = true, example = "system:redis:test:k1")
                              @RequestParam @NotBlank String key) {
        return Result.success(stringRedisTemplate.opsForValue().get(key));
    }

    @ApiOperation("删除Key")
    @DeleteMapping("/del")
    public Result<Boolean> del(@ApiParam(value = "Key", required = true, example = "system:redis:test:k1")
                               @RequestParam @NotBlank String key) {
        Boolean deleted = stringRedisTemplate.delete(key);
        return Result.success(Boolean.TRUE.equals(deleted));
    }

    @ApiOperation("获取Key剩余TTL(秒)")
    @GetMapping("/ttl")
    public Result<Long> ttl(@ApiParam(value = "Key", required = true, example = "system:redis:test:k1")
                            @RequestParam @NotBlank String key) {
        Long ttl = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return Result.success(ttl);
    }

    @ApiOperation("自增(数值)")
    @PostMapping("/incr")
    public Result<Long> incr(@ApiParam(value = "Key", required = true, example = "system:redis:test:counter")
                             @RequestParam @NotBlank String key,
                             @ApiParam(value = "步长，不传默认为1", example = "1")
                             @RequestParam(required = false) Long delta) {
        Long value = delta == null ? stringRedisTemplate.opsForValue().increment(key) : stringRedisTemplate.opsForValue().increment(key, delta);
        return Result.success(value);
    }
}

