package com.example.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.User;

/**
 * 用户Service接口
 * 
 * @author admin
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户列表
     * 
     * @param current 当前页
     * @param size 每页大小
     * @param username 用户名（模糊查询）
     * @param phone 手机号（模糊查询）
     * @return 用户分页数据
     */
    Page<User> listUsers(Long current, Long size, String username, String phone);

    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 新增用户
     * 
     * @param user 用户信息
     * @return 是否成功
     */
    boolean addUser(User user);

    /**
     * 修改用户
     * 
     * @param user 用户信息
     * @return 是否成功
     */
    boolean updateUser(User user);

    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
}