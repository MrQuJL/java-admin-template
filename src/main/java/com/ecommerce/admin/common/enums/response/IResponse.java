package com.ecommerce.admin.common.enums.response;

/**
 * 响应接口
 * 定义响应枚举的基本方法，实现多态效果
 */
public interface IResponse {
    
    /**
     * 获取状态码
     * @return 状态码
     */
    Integer getCode();
    
    /**
     * 获取状态描述
     * @return 状态描述
     */
    String getMessage();
}