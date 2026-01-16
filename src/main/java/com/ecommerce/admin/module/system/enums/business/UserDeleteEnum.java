package com.ecommerce.admin.module.system.enums.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户删除状态枚举
 * 用于表示系统用户的逻辑删除状态
 */
@Getter
@AllArgsConstructor
public enum UserDeleteEnum {
    
    /**
     * 未删除状态
     */
    NOT_DELETED(0, "未删除"),
    
    /**
     * 已删除状态
     */
    DELETED(1, "已删除");
    
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
     * @return UserDeleteEnum
     */
    public static UserDeleteEnum getByCode(Integer code) {
        for (UserDeleteEnum deleteStatus : values()) {
            if (deleteStatus.getCode().equals(code)) {
                return deleteStatus;
            }
        }
        return null;
    }
    
    /**
     * 检查是否为未删除状态
     * @return boolean
     */
    public boolean isNotDeleted() {
        return this == NOT_DELETED;
    }
    
    /**
     * 检查是否为已删除状态
     * @return boolean
     */
    public boolean isDeleted() {
        return this == DELETED;
    }
}