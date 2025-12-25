package com.example.javaadmintemplate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.javaadmintemplate.entity.User;
import com.example.javaadmintemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 后台管理页面控制器
 * 处理前端页面的请求
 *
 * @author example
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 首页 - 用户列表
     * GET /admin/users
     */
    @GetMapping("/users")
    public String getUserListPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Model model) {

        Page<User> userPage = new Page<>(page, size);
        IPage<User> userList = userService.page(userPage);

        model.addAttribute("userList", userList);
        return "user/list";
    }

    /**
     * 新增用户页面
     * GET /admin/users/add
     */
    @GetMapping("/users/add")
    public String addUserPage() {
        return "user/add";
    }

    /**
     * 编辑用户页面
     * GET /admin/users/edit/{id}
     */
    @GetMapping("/users/edit/{id}")
    public String editUserPage(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    /**
     * 仪表盘
     * GET /admin
     */
    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }
}