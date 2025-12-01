package com.ecommerce.util;

import com.ecommerce.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${app.jwt.secret:ecommerce-secret-key-for-jwt-token-generation}")
    private String secret;

    @Value("${app.jwt.expiration:86400}") // 默认24小时
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成Token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", ((User) userDetails).getId());
        claims.put("email", userDetails.getUsername());
        claims.put("sub", userDetails.getUsername());

        Date expiryDate = Date.from(ZonedDateTime.now().plusSeconds(expiration).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从Token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Long.valueOf(claims.get("userId").toString());
        } catch (Exception e) {
            log.error("从Token获取用户ID失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从Token中获取邮箱
     */
    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            log.error("从Token获取邮箱失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从Token中获取所有Claims
     */
    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("从Token获取Claims失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证Token
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取Token过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * 检查Token是否过期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    /**
     * 生成Token（重载方法）
     */
    public String generateToken(Long userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("sub", email);

        Date expiryDate = Date.from(ZonedDateTime.now().plusSeconds(expiration).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 刷新Token（生成新Token）
     */
    public String refreshToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            if (claims == null) {
                return null;
            }

            Long userId = Long.valueOf(claims.get("userId").toString());
            String email = claims.getSubject();

            return generateToken(userId, email);
        } catch (Exception e) {
            log.error("刷新Token失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取Token过期时间戳
     */
    public Long getExpirationTime() {
        return expiration; // 返回秒数
    }

    /**
     * 获取Token过期时间（日期格式）
     */
    public LocalDateTime getExpirationDateTime() {
        return ZonedDateTime.now()
                .plusSeconds(expiration)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}