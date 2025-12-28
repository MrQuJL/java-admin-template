package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.common.Result;
import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理Controller
 * 
 * @author admin
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询用户列表", description = "支持按用户名、手机号模糊查询")
    public Result<Page<User>> listUsers(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") Long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Long size,
            @Parameter(description = "用户名（模糊查询）") @RequestParam(required = false) String username,
            @Parameter(description = "手机号（模糊查询）") @RequestParam(required = false) String phone) {
        
        Page<User> page = userService.listUsers(current, size, username, phone);
        return Result.success(page);
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/get")
    @Operation(summary = "根据ID查询用户", description = "根据用户ID查询用户详细信息")
    public Result<User> getUserById(
            @Parameter(description = "用户ID", required = true) 
            @RequestParam(required = true) Long id) {
        
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    /**
     * 新增用户
     */
    @PostMapping("/add")
    @Operation(summary = "新增用户", description = "创建新用户")
    public Result<Void> addUser(
            @Parameter(description = "用户信息", required = true) @Valid @RequestBody User user) {
        
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @Operation(summary = "修改用户", description = "更新用户信息")
    public Result<Void> updateUser(
            @Parameter(description = "用户信息", required = true) @Valid @RequestBody User user) {
        
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID", required = true) @RequestParam Long id) {
        
        userService.deleteUser(id);
        return Result.success("删除用户成功");
    }
}