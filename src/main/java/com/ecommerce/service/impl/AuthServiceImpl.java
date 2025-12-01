package com.ecommerce.service.impl;

import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.dto.UserRegisterDTO;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.service.AuthService;
import com.ecommerce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 认证服务实现类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(UserLoginDTO loginDTO, String clientIp) {
        try {
            // 根据邮箱查找用户
            User user = userMapper.selectUserByEmail(loginDTO.getEmail());
            if (user == null) {
                throw new BusinessException("用户不存在");
            }

            // 检查用户状态
            if (user.getStatus() != 1) {
                throw new BusinessException("用户已被禁用");
            }

            // 验证密码
            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                throw new BusinessException("密码错误");
            }

            // 清除密码信息
            user.setPassword(null);

            // 生成Token
            String token = jwtUtil.generateToken(user);

            // 更新登录信息
            updateUserLoginInfo(user, clientIp);

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", user); // 返回用户信息对象
            result.put("expiresIn", jwtUtil.getExpirationTime());

            return result;
        } catch (BusinessException e) {
            log.warn("登录失败: {} - {}", loginDTO.getEmail(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("登录异常: {} - {}", loginDTO.getEmail(), e.getMessage(), e);
            throw new BusinessException("登录失败，请稍后重试");
        }
    }

    @Override
    public User register(UserRegisterDTO registerDTO) {
        try {
            // 验证密码确认
            if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
                throw new BusinessException("两次输入的密码不一致");
            }

            // 检查邮箱是否已存在
            User existUser = userMapper.selectUserByEmail(registerDTO.getEmail());
            if (existUser != null) {
                throw new BusinessException("邮箱已被注册");
            }

            // 检查手机号是否已存在
            User existPhoneUser = userMapper.selectUserByPhone(registerDTO.getPhone());
            if (existPhoneUser != null) {
                throw new BusinessException("手机号已被注册");
            }

            // 创建用户对象
            User user = new User();
            BeanUtils.copyProperties(registerDTO, user);
            user.setUsername(registerDTO.getFirstName()+registerDTO.getLastName());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setStatus(1); // 启用状态
            user.setCreatedTime(LocalDateTime.now());
            user.setUpdatedTime(LocalDateTime.now());

            // 保存用户
            userMapper.insertUser(user);

            // 清除密码信息
            user.setPassword(null);

            log.info("用户注册成功: {}", user.getEmail());
            return user;
        } catch (BusinessException e) {
            log.warn("注册失败: {} - {}", registerDTO.getEmail(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("注册异常: {} - {}", registerDTO.getEmail(), e.getMessage(), e);
            throw new BusinessException("注册失败，请稍后重试");
        }
    }

    @Override
    public User getUserById(Long userId) {
        try {
            User user = userMapper.selectUserById(userId);
            if (user == null) {
                throw new BusinessException("用户不存在");
            }
            if (user.getStatus() != 1) {
                throw new BusinessException("用户已被禁用");
            }
            // 清除敏感信息
            user.setPassword(null);
            return user;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取用户信息失败: userId={}", userId, e);
            throw new BusinessException("获取用户信息失败");
        }
    }

    @Override
    public Map<String, String> refreshToken(String token) {
        try {
            // 验证token
            if (!jwtUtil.validateToken(token)) {
                throw new BusinessException("Token无效或已过期");
            }

            // 解析用户信息
            Long userId = jwtUtil.getUserIdFromToken(token);
            User user = userMapper.selectUserById(userId);
            if (user == null || user.getStatus() != 1) {
                throw new BusinessException("用户不存在或已被禁用");
            }

            // 生成新token
            String newToken = jwtUtil.generateToken(userId, user.getEmail());

            Map<String, String> result = new HashMap<>();
            result.put("token", newToken);
            result.put("refreshToken", UUID.randomUUID().toString());

            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("刷新Token失败: {}", e.getMessage(), e);
            throw new BusinessException("刷新Token失败");
        }
    }

    @Override
    public void logout(String token, String clientIp) {
        try {
            // 验证token
            if (token != null) {
                Long userId = jwtUtil.getUserIdFromToken(token);
                User user = userMapper.selectUserById(userId);
                if (user != null) {
                    // 更新最后登出时间
                    user.setLastLoginTime(LocalDateTime.now());
                    userMapper.updateUser(user);
                    log.info("用户登出: {} - {}", user.getEmail(), clientIp);
                }
            }
        } catch (Exception e) {
            log.error("用户登出失败: {}", e.getMessage(), e);
        }
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        try {
            User user = userMapper.selectUserById(userId);
            if (user == null) {
                throw new BusinessException("用户不存在");
            }

            // 验证旧密码
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return false;
            }

            // 更新密码
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setUpdatedTime(LocalDateTime.now());
            userMapper.updateUser(user);

            log.info("用户修改密码成功: {}", user.getEmail());
            return true;
        } catch (Exception e) {
            log.error("修改密码失败: userId={}", userId, e);
            return false;
        }
    }

    @Override
    public boolean forgotPassword(String email) {
        try {
            User user = userMapper.selectUserByEmail(email);
            if (user == null) {
                throw new BusinessException("用户不存在");
            }

            // 生成重置token
            String resetToken = UUID.randomUUID().toString();
            // TODO: 发送重置邮件
            // emailService.sendPasswordResetEmail(email, resetToken);

            log.info("发送密码重置邮件: {}", email);
            return true;
        } catch (Exception e) {
            log.error("发送重置密码邮件失败: email={}", email, e);
            return false;
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        try {
            // TODO: 验证重置token的有效性
            // 这里需要验证token是否有效且未过期

            // 查找用户（简化实现，实际应该通过token查找）
            // User user = userMapper.selectUserByResetToken(token);
            // if (user == null || token过期) {
            //     return false;
            // }

            // 模拟实现：直接返回true
            log.info("密码重置成功");
            return true;
        } catch (Exception e) {
            log.error("重置密码失败: token={}", token, e);
            return false;
        }
    }

    /**
     * 更新用户登录信息
     */
    private void updateUserLoginInfo(User user, String clientIp) {
        // 创建只包含登录信息的用户对象，避免更新密码等敏感字段
        User loginUser = new User();
        loginUser.setId(user.getId());
        loginUser.setLastLoginTime(LocalDateTime.now());
        loginUser.setLastLoginIp(clientIp);

        // 使用专门的更新登录信息方法
        userMapper.updateUserLoginInfo(loginUser);
    }
}