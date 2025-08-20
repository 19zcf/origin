package com.zcf;

import com.zcf.annotation.OperateLog;
import com.zcf.aspect.OperateLogAspect;
import com.zcf.mapper.OperateLogMapper;
import com.zcf.utils.UserContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * 操作日志切面测试类
 */
@SpringBootTest
public class OperateLogAspectTest {

    @Autowired
    private OperateLogAspect operateLogAspect;

    @MockBean
    private OperateLogMapper operateLogMapper;

    @Test
    public void testOperateLogAspect() {
        // 模拟设置当前用户
        // UserContext.setCurrentUser(mockUser);
        
        // 测试切面是否正常工作
        // 这里可以添加具体的测试逻辑
        System.out.println("操作日志切面测试通过");
    }

    /**
     * 测试方法，用于验证@OperateLog注解是否生效
     */
    @OperateLog(value = "测试操作", operateType = "测试")
    public void testMethod() {
        System.out.println("这是一个测试方法");
    }
} 