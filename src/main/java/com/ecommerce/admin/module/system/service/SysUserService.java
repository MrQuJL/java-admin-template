package com.ecommerce.admin.module.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.admin.module.system.dto.SysUserDTO;
import com.ecommerce.admin.module.system.entity.SysUser;
import com.ecommerce.admin.module.system.vo.SysUserVO;

import java.util.List;

/**
 * 系统用户Service接口
 */
public interface SysUserService extends IService<SysUser> {
    
    /**
     * 分页查询用户
     * @param page 当前页码
     * @param size 每页大小
     * @param username 用户名（模糊查询）
     * @return IPage<SysUserVO>
     */
    IPage<SysUserVO> getUserPage(Integer page, Integer size, String username);
    
    /**
     * 根据ID获取用户详情
     * @param id 用户ID
     * @return SysUserVO
     */
    SysUserVO getUserDetail(Long id);

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
     * @return SysUserVO
     */
    SysUserVO createUser(SysUserDTO userDTO);
    
    /**
     * 更新用户
     * @param userDTO 用户DTO
     * @return SysUserVO
     */
    SysUserVO updateUser(SysUserDTO userDTO);
    
    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);
    
    /**
     * 获取所有用户数据
     * @return 所有用户列表
     */
    List<SysUser> listAll();
}