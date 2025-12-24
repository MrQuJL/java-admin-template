package com.example.javaadmintemplate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.javaadmintemplate.entity.User;
import com.example.javaadmintemplate.mapper.UserMapper;
import com.example.javaadmintemplate.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 * 
 * @author example
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 继承ServiceImpl即可获得基本的CRUD操作实现
    // 如需自定义业务方法的实现，可以在此添加
}
