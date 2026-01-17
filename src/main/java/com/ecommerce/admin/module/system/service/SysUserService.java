package com.ecommerce.admin.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.admin.module.system.dto.SysUserDTO;
import com.ecommerce.admin.module.system.entity.SysUser;
import java.util.List;

/**
 * 系统用户Service接口
 */
public interface SysUserService extends IService<SysUser> {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return SysUser
     */
    SysUser getByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return SysUser
     */
    SysUser getByEmail(String email);
    
    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return SysUser
     */
    SysUser getByPhone(String phone);
    
    /**
     * 创建用户
     * @param userDTO 用户DTO
     * @return SysUser
     */
    SysUser createUser(SysUserDTO userDTO);
    
    /**
     * 更新用户
     * @param id 用户ID
     * @param userDTO 用户DTO
     * @return SysUser
     */
    SysUser updateUser(Long id, SysUserDTO userDTO);
    
    /**
     * 获取所有用户数据
     * @return 所有用户列表
     */
    List<SysUser> listAll();
}