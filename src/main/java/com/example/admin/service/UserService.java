package com.example.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.User;

public interface UserService extends IService<User> {
    User getUserById(Long id);

    Page<User> pageUser(Integer current, Integer size, User user);
}
