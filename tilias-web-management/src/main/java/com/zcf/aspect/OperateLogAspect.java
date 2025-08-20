package com.zcf.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcf.annotation.OperateLog;
import com.zcf.mapper.OperateLogMapper;
import com.zcf.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面
 * 用于记录系统增删改操作的操作日志
 */
@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 定义切点：拦截controller包下所有带有@OperateLog注解的方法
     */
    @Pointcut("execution(* com.zcf.controller.*.*(..)) && @annotation(com.zcf.annotation.OperateLog)")
    public void operateLogPointcut() {}

    /**
     * 环绕通知：记录操作日志
     */
    @Around("operateLogPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        Exception exception = null;

        try {
            // 执行目标方法
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            // 记录操作日志
            try {
                recordOperateLog(joinPoint, result, startTime, exception);
            } catch (Exception e) {
                log.error("记录操作日志失败", e);
            }
        }
    }

    /**
     * 记录操作日志
     */
    private void recordOperateLog(ProceedingJoinPoint joinPoint, Object result, long startTime, Exception exception) {
        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            
            // 获取注解信息
            OperateLog operateLogAnnotation = method.getAnnotation(OperateLog.class);
            
            // 创建日志对象
            com.zcf.pojo.OperateLog operateLog = new com.zcf.pojo.OperateLog();
            
            // 设置操作人ID
            operateLog.setOperateEmpId(UserContext.getCurrentUserId());
            
            // 设置操作时间
            operateLog.setOperateTime(LocalDateTime.now());
            
            // 设置目标类名
            operateLog.setClassName(joinPoint.getTarget().getClass().getName());
            
            // 设置方法名
            operateLog.setMethodName(method.getName());
            
            // 设置方法参数
            String methodParams = convertArgsToString(joinPoint.getArgs());
            operateLog.setMethodParams(methodParams);
            
            // 设置返回值
            String returnValue = "";
            if (result != null) {
                if (result instanceof String) {
                    returnValue = (String) result;
                } else {
                    returnValue = objectMapper.writeValueAsString(result);
                }
            }
            // 限制返回值长度，避免过长
            if (returnValue.length() > 2000) {
                returnValue = returnValue.substring(0, 2000);
            }
            operateLog.setReturnValue(returnValue);
            
            // 设置执行耗时
            long endTime = System.currentTimeMillis();
            operateLog.setCostTime(endTime - startTime);
            
            // 保存日志
            operateLogMapper.insert(operateLog);
            
            log.info("操作日志记录成功: {}", operateLog);
            
        } catch (Exception e) {
            log.error("记录操作日志失败", e);
        }
    }

    /**
     * 将方法参数转换为字符串
     */
    private String convertArgsToString(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                
                Object arg = args[i];
                if (arg == null) {
                    sb.append("null");
                } else if (arg instanceof String || arg instanceof Number || arg instanceof Boolean) {
                    sb.append(arg.toString());
                } else {
                    // 对于复杂对象，使用JSON序列化
                    try {
                        String json = objectMapper.writeValueAsString(arg);
                        // 限制长度
                        if (json.length() > 500) {
                            json = json.substring(0, 500) + "...";
                        }
                        sb.append(json);
                    } catch (JsonProcessingException e) {
                        sb.append(arg.getClass().getSimpleName()).append("@").append(System.identityHashCode(arg));
                    }
                }
            }
            sb.append("]");
            
            String result = sb.toString();
            // 限制总长度
            if (result.length() > 2000) {
                result = result.substring(0, 2000);
            }
            return result;
            
        } catch (Exception e) {
            log.error("转换方法参数失败", e);
            return "参数转换失败";
        }
    }
} 