package com.zcf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 * 用于标记需要记录操作日志的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {
    
    /**
     * 操作描述
     */
    String value() default "";
    
    /**
     * 操作类型：增、删、改
     */
    String operateType() default "";
} 