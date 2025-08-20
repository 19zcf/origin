package com.zcf.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT令牌工具类
 * 用于生成和解析JWT令牌
 */
@Component
public class JwtUtils {

    /**
     * 密钥
     */
    private static final String SECRET = "secret";
    
    /**
     * 过期时间：24小时（毫秒）
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     * 
     * @param claims 要存储的数据
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET) // 签名算法
                .addClaims(claims) // 添加自定义属性
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 设置过期时间
                .compact();
    }

    /**
     * 解析JWT令牌
     * 
     * @param token JWT令牌字符串
     * @return 解析后的数据
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证令牌是否过期
     * 
     * @param token JWT令牌字符串
     * @return true表示已过期，false表示未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 从令牌中获取指定键的值
     * 
     * @param token JWT令牌字符串
     * @param key 键名
     * @return 对应的值
     */
    public Object getValueFromToken(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key);
    }

    /**
     * 从令牌中获取用户ID
     * 
     * @param token JWT令牌字符串
     * @return 用户ID
     */
    public Integer getUserIdFromToken(String token) {
        Object id = getValueFromToken(token, "id");
        if (id instanceof Integer) {
            return (Integer) id;
        } else if (id instanceof Long) {
            return ((Long) id).intValue();
        }
        return null;
    }

    /**
     * 从令牌中获取用户名
     * 
     * @param token JWT令牌字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Object username = getValueFromToken(token, "username");
        return username != null ? username.toString() : null;
    }
} 