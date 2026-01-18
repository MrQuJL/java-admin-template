package com.ecommerce.admin.module.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.admin.common.exception.BusinessException;
import com.ecommerce.admin.module.system.dto.SysUserDTO;
import com.ecommerce.admin.module.system.entity.SysUser;
import com.ecommerce.admin.module.system.enums.response.SystemResponseEnum;
import com.ecommerce.admin.module.system.mapper.SysUserMapper;
import com.ecommerce.admin.module.system.service.SysUserService;
import com.ecommerce.admin.module.system.vo.SysUserVO;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 系统用户Service实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 分页查询用户
     * @param page 当前页码
     * @param size 每页大小
     * @param username 用户名（模糊查询）
     * @return IPage<SysUserVO>
     */
    @Override
    public IPage<SysUserVO> getUserPage(Integer page, Integer size, String username) {
        // 创建分页对象
        Page<SysUser> pageParam = new Page<>(page, size);
        
        // 链式调用查询
        IPage<SysUser> userPage = this.lambdaQuery()
                .like(StringUtils.isNotBlank(username), SysUser::getUsername, username)
                .page(pageParam);
        
        // 转换为VO对象
        return userPage.convert(user -> modelMapper.map(user, SysUserVO.class));
    }

    /**
     * 根据ID获取用户详情
     * @param id 用户ID
     * @return SysUserVO
     */
    @Override
    public SysUserVO getUserDetail(Long id) {
        // 查询用户
        SysUser user = this.getById(id);
        
        if (user == null) {
            throw new BusinessException(SystemResponseEnum.USER_NOT_EXIST);
        }
        
        // 转换为VO对象
        return modelMapper.map(user, SysUserVO.class);
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return SysUser
     */
    @Override
    public SysUser getByUsername(String username) {
        return this.lambdaQuery()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getDeleted, 0)
                .one();
    }

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return SysUser
     */
    @Override
    public SysUser getByEmail(String email) {
        return this.lambdaQuery()
                .eq(SysUser::getEmail, email)
                .eq(SysUser::getDeleted, 0)
                .one();
    }

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return SysUser
     */
    @Override
    public SysUser getByPhone(String phone) {
        return this.lambdaQuery()
                .eq(SysUser::getPhone, phone)
                .eq(SysUser::getDeleted, 0)
                .one();
    }

    /**
     * 创建用户
     * @param userDTO 用户DTO
     * @return SysUserVO
     */
    @Override
    public SysUserVO createUser(SysUserDTO userDTO) {
        // 检查用户名是否已存在
        if (this.getByUsername(userDTO.getUsername()) != null) {
            throw new BusinessException(SystemResponseEnum.USERNAME_EXIST);
        }
        
        // 检查邮箱是否已存在
        if (this.getByEmail(userDTO.getEmail()) != null) {
            throw new BusinessException(SystemResponseEnum.EMAIL_EXIST);
        }
        
        // 检查手机号是否已存在
        if (this.getByPhone(userDTO.getPhone()) != null) {
            throw new BusinessException(SystemResponseEnum.PHONE_EXIST);
        }
        
        // 转换为实体对象
        SysUser user = modelMapper.map(userDTO, SysUser.class);
        
        // 设置默认值
        if (user.getIsActive() == null) {
            user.setIsActive(1);
        }
        
        // 保存用户
        this.save(user);
        
        // 转换为VO对象
        return modelMapper.map(user, SysUserVO.class);
    }
    
    /**
     * 更新用户
     * @param userDTO 用户DTO
     * @return SysUserVO
     */
    @Override
    public SysUserVO updateUser(SysUserDTO userDTO) {
        Long id = userDTO.getId();
        // 检查用户是否存在
        SysUser existingUser = this.getById(id);
        if (existingUser == null) {
            throw new BusinessException(SystemResponseEnum.USER_NOT_EXIST);
        }
        
        // 检查用户名是否已被其他用户使用
        SysUser userByUsername = this.getByUsername(userDTO.getUsername());
        if (userByUsername != null && !userByUsername.getId().equals(id)) {
            throw new BusinessException(SystemResponseEnum.USERNAME_EXIST);
        }
        
        // 检查邮箱是否已被其他用户使用
        SysUser userByEmail = this.getByEmail(userDTO.getEmail());
        if (userByEmail != null && !userByEmail.getId().equals(id)) {
            throw new BusinessException(SystemResponseEnum.EMAIL_EXIST);
        }
        
        // 检查手机号是否已被其他用户使用
        SysUser userByPhone = this.getByPhone(userDTO.getPhone());
        if (userByPhone != null && !userByPhone.getId().equals(id)) {
            throw new BusinessException(SystemResponseEnum.PHONE_EXIST);
        }
        
        // 转换为实体对象
        SysUser user = modelMapper.map(userDTO, SysUser.class);
        
        // 更新用户
        this.updateById(user);
        
        // 转换为VO对象
        return modelMapper.map(user, SysUserVO.class);
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     */
    @Override
    public void deleteUser(Long id) {
        // 检查用户是否存在
        if (this.getById(id) == null) {
            throw new BusinessException(SystemResponseEnum.USER_NOT_EXIST);
        }
        
        // 删除用户（逻辑删除）
        this.removeById(id);
    }
    
    /**
     * 获取所有用户数据
     * @return 所有未删除的用户列表
     */
    @Override
    public List<SysUser> listAll() {
        return this.lambdaQuery()
                .eq(SysUser::getDeleted, 0)
                .list();
    }
}