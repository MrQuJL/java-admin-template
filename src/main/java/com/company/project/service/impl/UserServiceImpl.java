package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.User;
import com.company.project.mapper.UserMapper;
import com.company.project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User createUser(User user) {
        save(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(getById(id));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return Optional.ofNullable(getOne(queryWrapper));
    }

    @Override
    public User updateUser(User user) {
        updateById(user);
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        return removeById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return list();
    }

    @Override
    public List<User> getUsersByPage(int page, int size) {
        Page<User> userPage = new Page<>(page, size);
        page(userPage);
        return userPage.getRecords();
    }
}