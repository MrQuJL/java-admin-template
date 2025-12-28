package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.admin.common.BusinessException;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new BusinessException("用户ID不能为空");
        }
        return this.lambdaQuery()
                .eq(User::getId, id)
                .one();
    }

    @Override
    public Page<User> pageUser(Integer current, Integer size, User user) {
        Page<User> page = new Page<>(current, size);
        return this.lambdaQuery()
                .eq(user.getId() != null, User::getId, user.getId())
                .like(StringUtils.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
                .like(StringUtils.isNotBlank(user.getNickname()), User::getNickname, user.getNickname())
                .like(StringUtils.isNotBlank(user.getEmail()), User::getEmail, user.getEmail())
                .eq(user.getStatus() != null, User::getStatus, user.getStatus())
                .orderByDesc(User::getCreateTime)
                .page(page);
    }
}
