package com.example.javaadmintemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 * 提供健康检查和测试接口，用于验证项目是否能正常运行
 *
 * @author example
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * 健康检查接口
     * GET /api/test/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "Java Admin Template is running successfully!");
        result.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }

    /**
     * 测试接口
     * GET /api/test/hello
     */
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from Java Admin Template!");
    }

}