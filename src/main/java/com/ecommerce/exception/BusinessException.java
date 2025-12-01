package com.ecommerce.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造函数
     */
    public BusinessException(String message) {
        this("BUSINESS_ERROR", message);
    }

    /**
     * 构造函数
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = "BUSINESS_ERROR";
        this.message = message;
    }

    /**
     * 构造函数
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}