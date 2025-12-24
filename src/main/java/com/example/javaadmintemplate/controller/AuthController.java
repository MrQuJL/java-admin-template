package com.example.javaadmintemplate.controller;

import com.example.javaadmintemplate.dto.LoginRequest;
import com.example.javaadmintemplate.dto.LoginResponse;
import com.example.javaadmintemplate.entity.User;
import com.example.javaadmintemplate.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 认证控制器
 * 处理登录、注册等认证相关请求
 *
 * @author example
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户登录
     * POST /api/auth/login
     *
     * @param loginRequest 登录请求参数
     * @return 登录响应，包含访问令牌和用户信息
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 根据用户名查询用户
        User user = userService.getByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 验证密码
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(null);
        }

        // 验证用户状态
        if (user.getStatus() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        // 生成简单的访问令牌（实际项目中建议使用JWT）
        String token = UUID.randomUUID().toString();

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setToken(token);

        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        response.setUser(userInfo);

        return ResponseEntity.ok(response);
    }

}
