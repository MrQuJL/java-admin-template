package com.example.javaadmintemplate.dto;

import lombok.Data;

/**
 * 登录请求DTO
 *
 * @author example
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
