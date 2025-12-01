package com.ecommerce.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * 用户实体类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class User implements UserDetails {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（加密）
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 判断用户是否启用
     */
    public boolean isEnabled() {
        return Integer.valueOf(1).equals(status);
    }

    /**
     * 获取性别描述
     */
    public String getGenderDesc() {
        if (gender == null) return "未知";
        switch (gender) {
            case 1: return "男";
            case 2: return "女";
            default: return "未知";
        }
    }

    // ===================== UserDetails接口实现 =====================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 简单实现，返回空权限列表
        // 实际项目中可以根据角色返回相应的权限
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 账户未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账户未锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 凭证未过期
        return true;
    }
}