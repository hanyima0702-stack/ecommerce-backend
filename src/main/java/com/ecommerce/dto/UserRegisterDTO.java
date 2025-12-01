package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册DTO
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
@Schema(description = "用户注册请求")
public class UserRegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名字
     */
    @NotBlank(message = "名字不能为空")
    @Size(min = 2, max = 50, message = "名字长度必须在2-50个字符之间")
    @Schema(description = "用户名字", example = "张")
    private String firstName;

    /**
     * 姓氏
     */
    @NotBlank(message = "姓氏不能为空")
    @Size(min = 2, max = 50, message = "姓氏长度必须在2-50个字符之间")
    @Schema(description = "用户姓氏", example = "三")
    private String lastName;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "用户邮箱", example = "user@example.com")
    private String email;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "用户手机号", example = "13800138000")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    @Schema(description = "用户密码", example = "password123")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", example = "password123")
    private String confirmPassword;

    /**
     * 性别：0-未知，1-男，2-女
     */
    @Schema(description = "用户性别", example = "1", allowableValues = {"0", "1", "2"})
    private Integer gender = 0;

    /**
     * 生日
     */
    @Schema(description = "用户生日", example = "1990-01-01")
    private String birthday;

    /**
     * 验证码
     */
    @Schema(description = "短信验证码", example = "123456")
    private String smsCode;

    /**
     * 验证码key
     */
    @Schema(description = "短信验证码key", example = "sms-key")
    private String smsCodeKey;

    /**
     * 是否同意服务条款
     */
    @Schema(description = "是否同意服务条款", example = "true")
    private Boolean agreeTerms = false;
}