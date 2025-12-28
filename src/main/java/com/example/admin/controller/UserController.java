package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.common.Result;
import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public Result<User> get(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @GetMapping("/list")
    public Result<Page<User>> page(@RequestParam(defaultValue = "1") Integer current,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    User user) {
        Page<User> page = userService.pageUser(current, size, user);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@Valid @RequestBody User user) {
        boolean success = userService.save(user);
        return success ? Result.success("新增成功") : Result.error("新增失败");
    }

    @PostMapping("/update")
    public Result<String> update(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            return Result.error("用户ID不能为空");
        }
        boolean success = userService.updateById(user);
        return success ? Result.success("修改成功") : Result.error("修改失败");
    }

    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = userService.removeById(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}
