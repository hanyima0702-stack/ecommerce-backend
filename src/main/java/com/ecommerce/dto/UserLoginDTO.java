package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录DTO
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
@Schema(description = "用户登录请求")
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱或用户名
     */
    @NotBlank(message = "邮箱或用户名不能为空")
    @Schema(description = "邮箱或用户名", example = "user@example.com")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "登录密码", example = "password123")
    private String password;

    /**
     * 是否记住我
     */
    @Schema(description = "是否记住登录状态", example = "false")
    private Boolean rememberMe = false;

    /**
     * 验证码（如果启用了验证码）
     */
    @Schema(description = "图形验证码", example = "1234")
    private String captcha;

    /**
     * 验证码key
     */
    @Schema(description = "验证码key", example = "captcha-key")
    private String captchaKey;
}