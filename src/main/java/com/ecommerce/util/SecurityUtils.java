package com.ecommerce.util;

import com.ecommerce.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类
 * 提供当前用户信息获取等功能
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
public class SecurityUtils {

    private static final ThreadLocal<String> CURRENT_TOKEN = new ThreadLocal<>();

    /**
     * 设置当前用户信息（由JWT过滤器调用）
     */
    public static void setCurrentUser(UserDetails userDetails, String token) {
        // 将当前用户的token存储到ThreadLocal中
        CURRENT_TOKEN.set(token);
        log.debug("设置当前用户token: {}", userDetails.getUsername());
    }

    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();
                return user.getId();
            }
            return null;
        } catch (Exception e) {
            log.error("获取当前用户ID失败", e);
            return null;
        }
    }

    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                return ((UserDetails) authentication.getPrincipal()).getUsername();
            }
            return null;
        } catch (Exception e) {
            log.error("获取当前用户名失败", e);
            return null;
        }
    }

    /**
     * 获取当前用户信息
     */
    public static Object getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                return authentication.getPrincipal();
            }
            return null;
        } catch (Exception e) {
            log.error("获取当前用户信息失败", e);
            return null;
        }
    }

    /**
     * 判断是否已认证
     */
    public static boolean isAuthenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
        } catch (Exception e) {
            log.error("判断用户认证状态失败", e);
            return false;
        }
    }

    /**
     * 获取认证token
     */
    public static String getCurrentToken() {
        try {
            return CURRENT_TOKEN.get();
        } catch (Exception e) {
            log.error("获取当前token失败", e);
            return null;
        }
    }

    /**
     * 清除安全上下文
     */
    public static void clearContext() {
        try {
            // 清除Spring Security上下文
            SecurityContextHolder.clearContext();
            // 清除ThreadLocal中的token
            CURRENT_TOKEN.remove();
            log.debug("已清除安全上下文");
        } catch (Exception e) {
            log.error("清除安全上下文失败", e);
        }
    }
}