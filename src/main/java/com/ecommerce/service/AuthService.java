package com.ecommerce.service;

import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.dto.UserRegisterDTO;
import com.ecommerce.entity.User;

import java.util.Map;

/**
 * 认证服务接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
public interface AuthService {

    /**
     * 用户登录
     * @param loginDTO 登录信息
     * @param clientIp 客户端IP
     * @return 包含token和用户信息的Map
     */
    Map<String, Object> login(UserLoginDTO loginDTO, String clientIp);

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册成功的用户对象，失败返回null
     */
    User register(UserRegisterDTO registerDTO);

    /**
     * 根据ID获取用户
     */
    User getUserById(Long userId);

    /**
     * 刷新Token
     * @param token 旧token
     * @return 包含新token的Map
     */
    Map<String, String> refreshToken(String token);

    /**
     * 用户登出
     */
    void logout(String token, String clientIp);

    /**
     * 修改密码
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 忘记密码
     */
    boolean forgotPassword(String email);

    /**
     * 重置密码
     */
    boolean resetPassword(String token, String newPassword);
}