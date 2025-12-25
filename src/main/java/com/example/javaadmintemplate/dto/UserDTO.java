package com.example.javaadmintemplate.dto;

import lombok.Data;

public class UserDTO {

    @Data
    public static class Create {
        private String username;
        private String password;
        private String realName;
        private String phone;
        private String email;
        private Integer status;
        private String remark;
    }

    @Data
    public static class Update {
        private String username;
        private String password; // Optional, only if changing
        private String realName;
        private String phone;
        private String email;
        private Integer status;
        private String remark;
    }
}
