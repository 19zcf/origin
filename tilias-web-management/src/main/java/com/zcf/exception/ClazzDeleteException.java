package com.zcf.exception;

/**
 * 班级删除异常
 * 当班级下有学生时，不允许删除班级
 */
public class ClazzDeleteException extends RuntimeException {
    
    public ClazzDeleteException(String message) {
        super(message);
    }
    
    public ClazzDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
} 