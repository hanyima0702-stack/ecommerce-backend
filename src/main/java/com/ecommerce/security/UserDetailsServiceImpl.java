package com.ecommerce.security;

import com.ecommerce.entity.User;
import com.ecommerce.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务实现类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userMapper.selectUserByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在: " + email);
            }

            if (user.getStatus() != 1) {
                throw new UsernameNotFoundException("用户已被禁用: " + email);
            }

            log.debug("成功加载用户信息: {}", email);
            return user;
        } catch (Exception e) {
            log.error("加载用户信息失败: {}", email, e);
            throw new UsernameNotFoundException("加载用户信息失败: " + email, e);
        }
    }
}