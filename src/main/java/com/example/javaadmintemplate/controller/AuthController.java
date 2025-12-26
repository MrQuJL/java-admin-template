package com.example.javaadmintemplate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.javaadmintemplate.common.Result;
import com.example.javaadmintemplate.dto.LoginDTO;
import com.example.javaadmintemplate.entity.User;
import com.example.javaadmintemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 认证控制器
 * 处理登录、登出等认证相关操作
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
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody @Validated LoginDTO loginDTO, HttpSession session) {
        // 根据用户名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginDTO.getUsername());
        User user = userService.getOne(wrapper);

        if (user == null) {
            return Result.error(401, "用户名或密码错误");
        }

        // 验证密码
        System.out.println("=== Login Debug ===");
        System.out.println("Input password: " + loginDTO.getPassword());
        System.out.println("Stored hash: " + user.getPassword());
        System.out.println("Hash length: " + user.getPassword().length());
        System.out.println("Hash starts with $2a: " + user.getPassword().startsWith("$2a"));

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
        System.out.println("Password matches: " + matches);

        if (!matches) {
            return Result.error(401, "用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            return Result.error(403, "账号已被禁用");
        }

        // 保存用户信息到session
        session.setAttribute("currentUser", user);

        // 返回用户信息（不包含密码）
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 获取当前登录用户
     * GET /api/auth/current
     */
    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(user);
    }
}
