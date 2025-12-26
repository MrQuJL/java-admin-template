package com.example.javaadmintemplate.dto;

import lombok.Data;

public class UserDTO {

    @Data
    public static class Create {
        @javax.validation.constraints.NotBlank(message = "用户名不能为空")
        @javax.validation.constraints.Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
        private String username;

        @javax.validation.constraints.NotBlank(message = "密码不能为空")
        @javax.validation.constraints.Size(min = 6, message = "密码长度不能少于6位")
        private String password;

        @javax.validation.constraints.NotBlank(message = "真实姓名不能为空")
        private String realName;

        @javax.validation.constraints.Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号码格式不正确")
        private String phone;

        @javax.validation.constraints.Email(message = "邮箱格式不正确")
        private String email;

        private Integer status;
        private String remark;
    }

    @Data
    public static class Update {
        @javax.validation.constraints.NotBlank(message = "用户名不能为空")
        @javax.validation.constraints.Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
        private String username;

        private String password; // Optional, only if changing

        @javax.validation.constraints.NotBlank(message = "真实姓名不能为空")
        private String realName;

        @javax.validation.constraints.Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号码格式不正确")
        private String phone;

        @javax.validation.constraints.Email(message = "邮箱格式不正确")
        private String email;

        private Integer status;
        private String remark;
    }
}
