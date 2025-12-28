package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.BusinessException;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户Service实现类
 * 
 * @author admin
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Page<User> listUsers(Long current, Long size, String username, String phone) {
        log.info("分页查询用户列表：current={}, size={}, username={}, phone={}", current, size, username, phone);
        
        Page<User> page = new Page<>(current, size);
        
        return this.lambdaQuery()
                .like(StringUtils.hasText(username), User::getUsername, username)
                .like(StringUtils.hasText(phone), User::getPhone, phone)
                .orderByDesc(User::getCreateTime)
                .page(page);
    }

    @Override
    public User getUserById(Long id) {
        log.info("根据ID查询用户：id={}", id);
        
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        return user;
    }

    @Override
    public boolean addUser(User user) {
        log.info("新增用户：username={}", user.getUsername());
        
        // 检查用户名是否已存在
        Long count = this.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .count();
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
        
        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        
        return this.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        log.info("修改用户：id={}", user.getId());
        
        // 检查用户是否存在
        User existUser = this.getById(user.getId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 如果修改用户名，检查用户名是否已存在
        if (StringUtils.hasText(user.getUsername()) && !user.getUsername().equals(existUser.getUsername())) {
            Long count = this.lambdaQuery()
                    .eq(User::getUsername, user.getUsername())
                    .ne(User::getId, user.getId())
                    .count();
            if (count > 0) {
                throw new BusinessException("用户名已存在");
            }
        }
        
        return this.updateById(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        log.info("删除用户：id={}", id);
        
        // 检查用户是否存在
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        return this.removeById(id);
    }
}