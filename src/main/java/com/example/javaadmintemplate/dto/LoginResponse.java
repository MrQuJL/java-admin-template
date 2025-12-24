package com.example.javaadmintemplate.dto;

import lombok.Data;

/**
 * 登录响应DTO
 *
 * @author example
 */
@Data
public class LoginResponse {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 用户信息
     */
    private UserInfo user;

    /**
     * 用户信息内部类
     */
    @Data
    public static class UserInfo {
        private Long id;
        private String username;
        private String realName;
        private String phone;
        private String email;
        private Integer status;
    }

}
