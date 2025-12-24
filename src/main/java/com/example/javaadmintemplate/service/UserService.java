package com.example.javaadmintemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.javaadmintemplate.entity.User;

/**
 * 用户Service接口
 *
 * @author example
 */
public interface UserService extends IService<User> {
    // 继承IService即可获得基本的CRUD操作
    // 如需自定义业务方法，可以在此添加

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return User 用户信息
     */
    User getByUsername(String username);
}