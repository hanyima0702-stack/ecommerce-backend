package com.ecommerce.controller;

import com.ecommerce.dto.Result;
import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.dto.UserRegisterDTO;
import com.ecommerce.entity.User;
import com.ecommerce.service.AuthService;
import com.ecommerce.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证控制器
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "用户认证", description = "用户登录、注册、认证相关API")
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户通过邮箱或手机号登录")
    public Result<Map<String, Object>> login(
            @Valid @RequestBody UserLoginDTO loginDTO,
            HttpServletRequest request) {
        try {
            // 记录登录IP
            String clientIp = getClientIpAddress(request);

            Map<String, Object> result = authService.login(loginDTO, clientIp);

            if (result != null) {
                log.info("用户登录成功: {}", loginDTO.getEmail());
                return Result.success(result);
            } else {
                return Result.error("登录失败，请检查邮箱和密码");
            }
        } catch (Exception e) {
            log.error("用户登录失败: {}", e.getMessage(), e);
            return Result.error("登录失败: " + e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册")
    public Result<User> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        try {
            User user = authService.register(registerDTO);

            if (user != null) {
                log.info("用户注册成功: {}", user.getEmail());
                // 返回用户信息但不包含敏感字段
                user.setPassword(null);
                return Result.success(user);
            } else {
                return Result.error("注册失败，请检查输入信息");
            }
        } catch (Exception e) {
            log.error("用户注册失败: {}", e.getMessage(), e);
            return Result.error("注册失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息", description = "根据token获取当前登录用户信息")
    public Result<User> getCurrentUser() {
        try {
            Long currentUserId = SecurityUtils.getCurrentUserId();
            if (currentUserId == null) {
                return Result.error("用户未登录");
            }

            User user = authService.getUserById(currentUserId);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 清除敏感信息
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            log.error("获取用户信息失败: {}", e.getMessage(), e);
            return Result.error("获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新Token", description = "刷新用户访问令牌")
    public Result<Map<String, String>> refreshToken(HttpServletRequest request) {
        try {
            String token = SecurityUtils.getCurrentToken();
            if (token == null) {
                return Result.error("未找到有效的token");
            }

            Map<String, String> result = authService.refreshToken(token);
            if (result != null) {
                return Result.success(result);
            } else {
                return Result.error("刷新token失败");
            }
        } catch (Exception e) {
            log.error("刷新token失败: {}", e.getMessage(), e);
            return Result.error("刷新token失败: " + e.getMessage());
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户安全退出")
    public Result<Void> logout(HttpServletRequest request) {
        try {
            String token = SecurityUtils.getCurrentToken();
            String clientIp = getClientIpAddress(request);

            if (token != null) {
                authService.logout(token, clientIp);
                log.info("用户登出成功");
            }

            // 清除安全上下文
            SecurityUtils.clearContext();

            return Result.success();
        } catch (Exception e) {
            log.error("用户登出失败: {}", e.getMessage(), e);
            return Result.error("登出失败: " + e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    @Operation(summary = "修改密码", description = "用户修改登录密码")
    public Result<Void> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        try {
            Long currentUserId = SecurityUtils.getCurrentUserId();
            if (currentUserId == null) {
                return Result.error("用户未登录");
            }

            boolean result = authService.changePassword(currentUserId, oldPassword, newPassword);
            if (result) {
                return Result.success();
            } else {
                return Result.error("原密码错误");
            }
        } catch (Exception e) {
            log.error("修改密码失败: {}", e.getMessage(), e);
            return Result.error("修改密码失败: " + e.getMessage());
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "忘记密码", description = "发送密码重置邮件")
    public Result<String> forgotPassword(
            @RequestParam String email) {
        try {
            boolean result = authService.forgotPassword(email);
            if (result) {
                return Result.success("密码重置邮件已发送");
            } else {
                return Result.error("发送失败，请检查邮箱地址");
            }
        } catch (Exception e) {
            log.error("忘记密码失败: {}", e.getMessage(), e);
            return Result.error("发送失败: " + e.getMessage());
        }
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    @Operation(summary = "重置密码", description = "通过重置token重置密码")
    public Result<Void> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            boolean result = authService.resetPassword(token, newPassword);
            if (result) {
                return Result.success();
            } else {
                return Result.error("重置失败，token可能已过期");
            }
        } catch (Exception e) {
            log.error("重置密码失败: {}", e.getMessage(), e);
            return Result.error("重置失败: " + e.getMessage());
        }
    }

    /**
     * 检查Token有效性
     */
    @GetMapping("/validate")
    @Operation(summary = "检查Token有效性", description = "验证当前token是否有效")
    public Result<Map<String, Object>> validateToken() {
        try {
            Long currentUserId = SecurityUtils.getCurrentUserId();
            if (currentUserId != null) {
                Map<String, Object> result = new HashMap<>();
                result.put("valid", true);
                result.put("userId", currentUserId);
                result.put("username", SecurityUtils.getCurrentUsername());
                return Result.success(result);
            } else {
                Map<String, Object> result = new HashMap<>();
                result.put("valid", false);
                return Result.success(result);
            }
        } catch (Exception e) {
            log.error("验证token失败: {}", e.getMessage(), e);
            Map<String, Object> result = new HashMap<>();
            result.put("valid", false);
            return Result.success(result);
        }
    }

    /**
     * 获取客户端真实IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String xRealIp = request.getHeader("X-Real-IP");

        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }

        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        String clientIp = request.getHeader("Proxy-Client-IP");
        if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
            return clientIp;
        }

        clientIp = request.getHeader("WL-Proxy-Client-IP");
        if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
            return clientIp;
        }

        clientIp = request.getHeader("HTTP_CLIENT_IP");
        if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
            return clientIp;
        }

        clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
            return clientIp.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}