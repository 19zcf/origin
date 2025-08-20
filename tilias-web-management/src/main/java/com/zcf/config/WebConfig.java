package com.zcf.config;

import com.zcf.interceptor.DemoInterceptor;
import com.zcf.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 * @author zhongzhong
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**");
    }



}
