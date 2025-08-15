package com.zcf;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class LogTest {

    private static final Logger logger= LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLog(){
//        System.out.println(LocalDateTime.now()+": 开始计算");
        logger.debug("开始计算");

        int sum=0;
        int[] nums={1,2,3,4,5,6,7,8,9,10};
        for (int i = 0; i < nums.length; i++) {
            sum+=nums[i];
        }

        logger.info("结果为："+sum);
//        System.out.println("结果为："+sum);
//        System.out.println(LocalDateTime.now()+": 结束计算");
        logger.debug("结束计算");
    }

}
