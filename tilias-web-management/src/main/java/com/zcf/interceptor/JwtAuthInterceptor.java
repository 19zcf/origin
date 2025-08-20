package com.zcf.interceptor;

import com.zcf.pojo.Emp;
import com.zcf.utils.JwtUtils;
import com.zcf.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT认证拦截器
 * 自动解析Token并设置用户上下文
 */
@Slf4j
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 获取Authorization头
            String authHeader = request.getHeader(TOKEN_HEADER);
            
            if (StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
                String token = authHeader.substring(TOKEN_PREFIX.length());
                
                // 解析Token
                JwtUtils jwtUtils = new JwtUtils();
                Integer userId = jwtUtils.getUserIdFromToken(token);
                String username = jwtUtils.getUsernameFromToken(token);
                
                if (userId != null && username != null) {
                    // 创建用户对象并设置到上下文
                    Emp emp = new Emp();
                    emp.setId(userId);
                    emp.setUsername(username);
                    
                    // 设置到用户上下文
                    UserContext.setCurrentUser(emp);
                    
                    log.debug("用户认证成功: userId={}, username={}", userId, username);
                } else {
                    log.warn("Token解析失败: userId={}, username={}", userId, username);
                }
            } else {
                log.debug("请求头中未找到有效的Authorization信息");
            }
        } catch (Exception e) {
            log.warn("JWT认证拦截器处理失败: {}", e.getMessage());
        }
        
        // 继续执行请求
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户上下文
        UserContext.setCurrentUser(null);
    }
} 