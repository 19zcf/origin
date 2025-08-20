package com.zcf.interceptor;

import com.zcf.pojo.Emp;
import com.zcf.utils.JwtUtils;
import com.zcf.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求路径
        String path = request.getRequestURI();

        //判断是否是登录请求
        if (path.contains("login")) {
            log.debug("登录请求: {}", path);
            return true;
        }

        //获取token
        String token = request.getHeader("token");

        //判断token是否为空
        if (token == null || token.equals("")) {
            log.warn("令牌为空，响应401");
            response.setStatus(401);
            return false;
        }

        //验证token
        try{
            // 解析Token并获取用户信息
            var claims = JwtUtils.parseToken(token);
            Integer userId = (Integer) claims.get("id");
            String username = (String) claims.get("username");
            
            if (userId != null && username != null) {
                // 创建用户对象并设置到上下文
                Emp emp = new Emp();
                emp.setId(userId);
                emp.setUsername(username);
                
                // 设置到用户上下文
                UserContext.setCurrentUser(emp);
                
                log.debug("用户认证成功: userId={}, username={}", userId, username);
            } else {
                log.warn("Token中用户信息不完整: userId={}, username={}", userId, username);
            }
        }catch (Exception e){
            log.warn("令牌验证失败，响应401");
            response.setStatus(401);
            return false;
        }

        //放行
        log.debug("令牌验证通过: {}", token);
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户上下文
        UserContext.setCurrentUser(null);
    }
}
