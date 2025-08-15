package com.zcf.exception;

import com.zcf.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler
    public Result handle(Exception e) {
        log.error("服务器发生异常:{}", e.getMessage());
        e.printStackTrace();
        return Result.error("服务器发生异常");
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("服务器发生异常:{}", e.getMessage());
        //获取错误信息
        String message = e.getMessage();
        int i=message.lastIndexOf("Duplicate entry");
        String errMsg=message.substring(i);
        String[] arr=errMsg.split(" ");
        return Result.error("数据已存在:"+arr[2]);
    }

}
