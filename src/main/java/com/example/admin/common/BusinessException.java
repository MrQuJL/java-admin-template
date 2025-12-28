package com.example.admin.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 业务异常类
 * 
 * @author admin
 */
@Getter
@Schema(description = "业务异常")
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    @Schema(description = "错误码")
    private Integer code;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String message;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}