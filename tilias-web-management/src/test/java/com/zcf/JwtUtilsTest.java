package com.zcf;

import com.zcf.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtUtils工具类测试
 */
@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 测试生成令牌
     */
    @Test
    public void testGenerateToken() {
        // 准备测试数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "admin");
        claims.put("role", "管理员");

        // 生成令牌
        String token = jwtUtils.generateToken(claims);

        // 验证令牌不为空
        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        System.out.println("生成的令牌: " + token);
    }

    /**
     * 测试解析令牌
     */
    @Test
    public void testParseToken() {
        // 准备测试数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 100);
        claims.put("username", "testuser");
        claims.put("email", "test@example.com");

        // 生成令牌
        String token = jwtUtils.generateToken(claims);

        // 解析令牌
        Claims parsedClaims = jwtUtils.parseToken(token);

        // 验证解析结果
        assertNotNull(parsedClaims);
        assertEquals(100, parsedClaims.get("id"));
        assertEquals("testuser", parsedClaims.get("username"));
        assertEquals("test@example.com", parsedClaims.get("email"));
        
        System.out.println("解析的令牌内容: " + parsedClaims);
    }

    /**
     * 测试令牌过期验证
     */
    @Test
    public void testTokenExpiration() {
        // 生成令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "admin");
        
        String token = jwtUtils.generateToken(claims);

        // 验证令牌未过期
        assertFalse(jwtUtils.isTokenExpired(token));
        
        System.out.println("令牌过期状态: " + jwtUtils.isTokenExpired(token));
    }

    /**
     * 测试从令牌中获取用户ID
     */
    @Test
    public void testGetUserIdFromToken() {
        // 准备测试数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 999);
        claims.put("username", "user999");

        // 生成令牌
        String token = jwtUtils.generateToken(claims);

        // 获取用户ID
        Integer userId = jwtUtils.getUserIdFromToken(token);

        // 验证结果
        assertNotNull(userId);
        assertEquals(999, userId);
        
        System.out.println("从令牌中获取的用户ID: " + userId);
    }

    /**
     * 测试从令牌中获取用户名
     */
    @Test
    public void testGetUsernameFromToken() {
        // 准备测试数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 888);
        claims.put("username", "user888");

        // 生成令牌
        String token = jwtUtils.generateToken(claims);

        // 获取用户名
        String username = jwtUtils.getUsernameFromToken(token);

        // 验证结果
        assertNotNull(username);
        assertEquals("user888", username);
        
        System.out.println("从令牌中获取的用户名: " + username);
    }

    /**
     * 测试从令牌中获取指定值
     */
    @Test
    public void testGetValueFromToken() {
        // 准备测试数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 777);
        claims.put("username", "user777");
        claims.put("department", "技术部");

        // 生成令牌
        String token = jwtUtils.generateToken(claims);

        // 获取部门信息
        Object department = jwtUtils.getValueFromToken(token, "department");

        // 验证结果
        assertNotNull(department);
        assertEquals("技术部", department);
        
        System.out.println("从令牌中获取的部门: " + department);
    }

    /**
     * 测试完整流程
     */
    @Test
    public void testCompleteFlow() {
        // 1. 准备用户数据
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", 12345);
        userData.put("username", "zhangsan");
        userData.put("role", "教师");
        userData.put("email", "zhangsan@school.com");

        // 2. 生成令牌
        String token = jwtUtils.generateToken(userData);
        System.out.println("生成的令牌: " + token);

        // 3. 解析令牌
        Claims claims = jwtUtils.parseToken(token);
        System.out.println("解析的令牌内容: " + claims);

        // 4. 验证令牌未过期
        assertFalse(jwtUtils.isTokenExpired(token));

        // 5. 获取用户信息
        Integer userId = jwtUtils.getUserIdFromToken(token);
        String username = jwtUtils.getUsernameFromToken(token);
        String role = (String) jwtUtils.getValueFromToken(token, "role");

        // 6. 验证获取的数据
        assertEquals(12345, userId);
        assertEquals("zhangsan", username);
        assertEquals("教师", role);

        System.out.println("用户ID: " + userId);
        System.out.println("用户名: " + username);
        System.out.println("角色: " + role);
    }
} 