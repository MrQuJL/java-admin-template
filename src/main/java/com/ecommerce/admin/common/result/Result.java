package com.ecommerce.admin.common.result;

import com.ecommerce.admin.common.enums.response.IResponse;
import com.ecommerce.admin.common.enums.response.ResponseEnum;

import lombok.Getter;

/**
 * 统一返回结果类
 * 用于 API 接口的统一响应格式
 */
@Getter
public class Result<T> {

    /**
     * 状态码，200 表示成功，其他值表示错误
     */
    private final Integer code;

    /**
     * 描述信息，用于提示调用方操作结果
     */
    private final String message;

    /**
     * 响应数据，根据具体接口返回不同的格式
     */
    private final T data;

    /**
     * 构造方法
     * @param code 状态码
     * @param message 描述信息
     * @param data 响应数据
     */
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> success() {
        return new Result<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功响应，带数据
     * @param data 响应数据
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功响应，自定义消息
     * @param message 描述信息
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(ResponseEnum.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功响应，自定义消息和数据
     * @param message 描述信息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResponseEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败响应
     * @param code 状态码
     * @param message 错误信息
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败响应，默认状态码
     * @param message 错误信息
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(ResponseEnum.SYSTEM_ERROR.getCode(), message, null);
    }

    /**
     * 从 IResponse 接口构造失败响应
     * @param response 响应对象，支持任何实现了 IResponse 接口的枚举
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> fail(IResponse response) {
        return new Result<>(response.getCode(), response.getMessage(), null);
    }

    /**
     * 从 IResponse 接口构造失败响应，自定义消息
     * @param response 响应对象，支持任何实现了 IResponse 接口的枚举
     * @param message 自定义错误信息
     * @param <T> 数据类型
     * @return Result<T>
     */
    public static <T> Result<T> fail(IResponse response, String message) {
        return new Result<>(response.getCode(), message, null);
    }
}