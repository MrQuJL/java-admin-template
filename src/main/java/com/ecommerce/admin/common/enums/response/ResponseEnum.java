package com.ecommerce.admin.common.enums.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码枚举
 * 定义系统中所有的状态码，包含成功和失败状态码
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum implements IResponse {
    SUCCESS(200, "success"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "未找到资源"),
    SYSTEM_ERROR(500, "系统错误"),
    
    BUSINESS_ERROR(600, "业务错误"),
    PRODUCT_NOT_EXIST(801, "商品不存在"),
    PRODUCT_STOCK_NOT_ENOUGH(802, "商品库存不足"),
    ORDER_NOT_EXIST(901, "订单不存在"),
    ORDER_STATUS_ERROR(902, "订单状态错误"),
    ORDER_PAY_ERROR(903, "支付失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态描述
     */
    private final String message;
}