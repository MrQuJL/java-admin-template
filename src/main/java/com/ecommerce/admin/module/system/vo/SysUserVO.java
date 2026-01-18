package com.ecommerce.admin.module.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户VO
 */
@Data
@ApiModel(description = "系统用户视图对象")
public class SysUserVO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", example = "管理员")
    private String realName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "admin@example.com")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "13800138000")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像URL", example = "https://example.com/avatar.png")
    private String avatar;

    /**
     * 是否激活: 0-禁用, 1-启用
     */
    @ApiModelProperty(value = "是否激活: 0-禁用, 1-启用", example = "1")
    private Integer isActive;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2024-01-01 12:00:00")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2024-01-01 12:00:00")
    private LocalDateTime updatedAt;
}