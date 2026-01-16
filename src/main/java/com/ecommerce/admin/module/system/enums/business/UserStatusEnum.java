package com.ecommerce.admin.module.system.enums.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 * 用于表示系统用户的激活状态
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    
    /**
     * 禁用状态
     */
    DISABLED(0, "禁用"),
    
    /**
     * 启用状态
     */
    ENABLED(1, "启用");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 状态描述
     */
    private final String desc;
    
    /**
     * 根据状态码获取枚举
     * @param code 状态码
     * @return UserStatusEnum
     */
    public static UserStatusEnum getByCode(Integer code) {
        for (UserStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
    
    /**
     * 检查状态是否为启用
     * @return boolean
     */
    public boolean isEnabled() {
        return this == ENABLED;
    }
    
    /**
     * 检查状态是否为禁用
     * @return boolean
     */
    public boolean isDisabled() {
        return this == DISABLED;
    }
}