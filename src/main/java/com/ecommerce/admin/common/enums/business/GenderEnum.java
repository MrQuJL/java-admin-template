package com.ecommerce.admin.common.enums.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 未知
     */
    UNKNOWN(0, "未知"),
    
    /**
     * 男
     */
    MALE(1, "男"),
    
    /**
     * 女
     */
    FEMALE(2, "女");

    /**
     * 性别码
     */
    private final Integer code;
    
    /**
     * 性别描述
     */
    private final String desc;
    
    /**
     * 根据性别码获取枚举
     * @param code 性别码
     * @return GenderEnum
     */
    public static GenderEnum getByCode(Integer code) {
        for (GenderEnum gender : values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        return UNKNOWN;
    }
}