package com.ecommerce.admin.common.enums.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是/否枚举
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum {

    /**
     * 否
     */
    NO(0, "否"),
    
    /**
     * 是
     */
    YES(1, "是");

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
     * @return YesNoEnum
     */
    public static YesNoEnum getByCode(Integer code) {
        for (YesNoEnum yesNo : values()) {
            if (yesNo.getCode().equals(code)) {
                return yesNo;
            }
        }
        return NO;
    }
    
    /**
     * 将布尔值转换为枚举
     * @param value 布尔值
     * @return YesNoEnum
     */
    public static YesNoEnum fromBoolean(Boolean value) {
        return value ? YES : NO;
    }
    
    /**
     * 将枚举转换为布尔值
     * @return boolean
     */
    public boolean toBoolean() {
        return this == YES;
    }
}