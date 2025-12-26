package com.example.javaadmintemplate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.javaadmintemplate.common.Result;
import com.example.javaadmintemplate.dto.UserDTO;
import com.example.javaadmintemplate.entity.User;
import com.example.javaadmintemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户控制器
 * RESTful风格API
 *
 * @author example
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取当前用户详情 (Mock as ID 1)
     * GET /api/users/profile
     */
    @GetMapping("/profile")
    public Result<User> getProfile() {
        // Mock current user as ID 1
        Long currentUserId = 1L;
        User user = userService.getById(currentUserId);
        if (user == null) {
            return Result.error(404, "User not found");
        }
        return Result.success(user);
    }

    /**
     * 更新当前用户信息 (Mock as ID 1)
     * PUT /api/users/profile
     */
    @PutMapping("/profile")
    public Result<User> updateProfile(@RequestBody @Validated UserDTO.Update userDTO) {
        // Mock current user as ID 1
        Long currentUserId = 1L;
        User existingUser = userService.getById(currentUserId);
        if (existingUser == null) {
            return Result.error(404, "User not found");
        }

        // Uniqueness Check (if username changed)
        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(existingUser.getUsername())) {
            if (userService.count(new QueryWrapper<User>().eq("username", userDTO.getUsername())) > 0) {
                return Result.error(400, "用户名已存在");
            }
        }

        if (userDTO.getUsername() != null)
            existingUser.setUsername(userDTO.getUsername());
        if (userDTO.getRealName() != null)
            existingUser.setRealName(userDTO.getRealName());
        if (userDTO.getPhone() != null)
            existingUser.setPhone(userDTO.getPhone());
        if (userDTO.getEmail() != null)
            existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getStatus() != null)
            existingUser.setStatus(userDTO.getStatus());
        if (userDTO.getRemark() != null)
            existingUser.setRemark(userDTO.getRemark());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        existingUser.setUpdateTime(LocalDateTime.now());

        userService.updateById(existingUser);
        return Result.success(existingUser);
    }

    /**
     * 获取用户列表（分页）
     * GET /api/users?page=1&size=10
     */
    @GetMapping
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {

        Page<User> userPage = new Page<>(page, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.like("username", keyword).or().like("email", keyword).or().like("real_name", keyword);
        }

        // 倒序排列
        wrapper.orderByDesc("create_time");

        IPage<User> userList = userService.page(userPage, wrapper);

        return Result.success(userList);
    }

    /**
     * 根据ID获取用户详情
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error(404, "User not found");
        }
        return Result.success(user);
    }

    /**
     * 创建新用户
     * POST /api/users
     */
    @PostMapping
    public Result<User> createUser(@RequestBody @Validated UserDTO.Create userDTO) {
        // Uniquess Check
        if (userService.count(new QueryWrapper<User>().eq("username", userDTO.getUsername())) > 0) {
            return Result.error(400, "用户名已存在");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRealName(userDTO.getRealName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());
        user.setRemark(userDTO.getRemark());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
        return Result.success(user);
    }

    /**
     * 更新用户信息
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody @Validated UserDTO.Update userDTO) {
        User existingUser = userService.getById(id);
        if (existingUser == null) {
            return Result.error(404, "User not found");
        }

        // Uniqueness Check (if username changed)
        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(existingUser.getUsername())) {
            if (userService.count(new QueryWrapper<User>().eq("username", userDTO.getUsername())) > 0) {
                return Result.error(400, "用户名已存在");
            }
        }

        if (userDTO.getUsername() != null)
            existingUser.setUsername(userDTO.getUsername());
        if (userDTO.getRealName() != null)
            existingUser.setRealName(userDTO.getRealName());
        if (userDTO.getPhone() != null)
            existingUser.setPhone(userDTO.getPhone());
        if (userDTO.getEmail() != null)
            existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getStatus() != null)
            existingUser.setStatus(userDTO.getStatus());
        if (userDTO.getRemark() != null)
            existingUser.setRemark(userDTO.getRemark());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        existingUser.setUpdateTime(LocalDateTime.now());

        userService.updateById(existingUser);
        return Result.success(existingUser);
    }

    /**
     * 删除用户
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        if (userService.count(new QueryWrapper<User>().eq("id", id)) == 0) {
            return Result.error(404, "User not found");
        }
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除用户
     * DELETE /api/users
     */
    @DeleteMapping
    public Result<Void> deleteUsers(@RequestBody List<Long> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 根据用户名查询用户
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public Result<List<User>> getUserByUsername(@PathVariable String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        List<User> users = userService.list(wrapper);
        return Result.success(users);
    }

    /**
     * 更新用户状态
     * PATCH /api/users/{id}/status
     */
    @PatchMapping("/{id}/status")
    public Result<User> updateUserStatus(@PathVariable Long id, @RequestBody Integer status) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error(404, "User not found");
        }
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success(user);
    }
}