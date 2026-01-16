package com.ecommerce.admin.common.exception;

import com.ecommerce.admin.common.enums.response.ResponseEnum;
import com.ecommerce.admin.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 * 统一处理所有控制器抛出的异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * @param e 业务异常
     * @return Result
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        // 记录业务异常日志
        log.error("业务异常：{}", e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     * @param e 参数校验异常
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 获取第一个校验失败的字段和错误信息
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        // 记录参数校验异常日志
        log.error("参数校验异常：{}", message, e);
        return Result.fail(ResponseEnum.PARAM_ERROR.getCode(), message);
    }
    
    /**
     * 处理请求参数缺失异常
     * @param e 请求参数缺失异常
     * @return Result
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 构造错误信息
        String message = e.getParameterName() + "不能为空";
        // 记录请求参数缺失异常日志
        log.error("请求参数缺失异常：{}", message, e);
        return Result.fail(ResponseEnum.PARAM_ERROR.getCode(), message);
    }
    
    /**
     * 处理请求参数约束违反异常
     * @param e 请求参数约束违反异常
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        // 获取第一个校验失败的错误信息
        String message = e.getConstraintViolations().iterator().next().getMessage();
        // 记录请求参数约束违反异常日志
        log.error("请求参数约束违反异常：{}", message, e);
        return Result.fail(ResponseEnum.PARAM_ERROR.getCode(), message);
    }
    
    /**
     * 处理绑定异常
     * @param e 绑定异常
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        // 获取第一个校验失败的字段和错误信息
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        // 记录绑定异常日志
        log.error("绑定异常：{}", message, e);
        return Result.fail(ResponseEnum.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理运行时异常
     * @param e 运行时异常
     * @return Result
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        // 记录运行时异常日志
        log.error("运行时异常：{}", e.getMessage(), e);
        return Result.fail(ResponseEnum.SYSTEM_ERROR.getCode(), "系统异常：" + e.getMessage());
    }

    /**
     * 处理系统异常
     * @param e 系统异常
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 记录系统异常日志
        log.error("系统异常：{}", e.getMessage(), e);
        return Result.fail(ResponseEnum.SYSTEM_ERROR.getCode(), "系统异常：" + e.getMessage());
    }
}