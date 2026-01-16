package com.ecommerce.admin.module.system.enums.response;

import com.ecommerce.admin.common.enums.response.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统模块响应枚举
 * 用于系统模块专属的业务状态定义
 */
@Getter
@AllArgsConstructor
public enum SystemResponseEnum implements IResponse {
    USER_NOT_EXIST(701, "用户不存在"),
    USER_PASSWORD_ERROR(702, "密码错误"),
    USER_ACCOUNT_LOCKED(703, "账户已锁定"),
    USER_PASSWORD_EXPIRED(704, "密码已过期"),
    USER_TOO_MANY_LOGIN_ATTEMPTS(705, "登录失败次数过多"),
    USERNAME_EXIST(706, "用户名已存在"),
    EMAIL_EXIST(707, "邮箱已存在"),
    PHONE_EXIST(708, "手机号已存在"),
    ROLE_NOT_EXIST(710, "角色不存在"),
    PERMISSION_DENIED(720, "权限不足");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 状态描述
     */
    private final String message;
}