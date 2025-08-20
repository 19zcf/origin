package com.zcf.utils;

import com.zcf.pojo.Emp;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户上下文工具类
 * 用于获取当前登录用户信息
 */
public class UserContext {
    
    private static final String USER_KEY = "currentUser";
    
    /**
     * 设置当前用户到请求属性中
     */
    public static void setCurrentUser(Emp user) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            request.setAttribute(USER_KEY, user);
        }
    }
    
    /**
     * 获取当前登录用户
     */
    public static Emp getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return (Emp) request.getAttribute(USER_KEY);
        }
        return null;
    }
    
    /**
     * 获取当前登录用户ID
     */
    public static Integer getCurrentUserId() {
        Emp user = getCurrentUser();
        return user != null ? user.getId() : null;
    }
} 