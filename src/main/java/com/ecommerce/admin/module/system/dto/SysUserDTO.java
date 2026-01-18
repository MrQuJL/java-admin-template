package com.ecommerce.admin.module.system.dto;

import com.ecommerce.admin.common.validation.AddGroup;
import com.ecommerce.admin.common.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 系统用户DTO
 */
@Data
@ApiModel(description = "系统用户数据传输对象")
public class SysUserDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1")
    @NotNull(message = "用户ID不能为空", groups = UpdateGroup.class)
    @Null(message = "创建时不能指定用户ID", groups = AddGroup.class)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "123456")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间", groups = {AddGroup.class, UpdateGroup.class})
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", required = true, example = "管理员")
    @NotBlank(message = "真实姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 2, max = 50, message = "真实姓名长度必须在2-50个字符之间", groups = {AddGroup.class, UpdateGroup.class})
    private String realName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = true, example = "admin@example.com")
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true, example = "13800138000")
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 11, max = 11, message = "手机号长度必须为11个字符", groups = {AddGroup.class, UpdateGroup.class})
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
    @Min(value = 0, message = "状态只能是0或1", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "状态只能是0或1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer isActive;
}