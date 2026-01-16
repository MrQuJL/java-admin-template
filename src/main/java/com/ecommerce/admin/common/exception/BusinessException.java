package com.ecommerce.admin.common.exception;

import com.ecommerce.admin.common.enums.response.IResponse;
import lombok.Getter;

/**
 * 业务异常类
 * 用于封装业务逻辑中的异常信息
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 构造方法
     * @param code 状态码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造方法
     * @param response 响应枚举，支持任何实现了 IResponse 接口的枚举
     */
    public BusinessException(IResponse response) {
        super(response.getMessage());
        this.code = response.getCode();
    }

    /**
     * 构造方法
     * @param response 响应枚举，支持任何实现了 IResponse 接口的枚举
     * @param message 自定义错误消息
     */
    public BusinessException(IResponse response, String message) {
        super(message);
        this.code = response.getCode();
    }
}