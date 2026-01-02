package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends IService<User> {

    /**
     * 创建用户
     * @param user 用户信息
     * @return 创建的用户
     */
    User createUser(User user);

    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户信息
     */
    Optional<User> getUserById(Long id);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    Optional<User> getUserByUsername(String username);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新后的用户
     */
    User updateUser(User user);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Long id);

    /**
     * 获取所有用户
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 分页获取用户
     * @param page 页码
     * @param size 每页数量
     * @return 用户列表
     */
    List<User> getUsersByPage(int page, int size);
}