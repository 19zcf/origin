package com.zcf.service;

import com.zcf.pojo.OperateLog;
import com.zcf.pojo.OperateLogQueryParam;
import com.zcf.pojo.PageResult;

/**
 * 操作日志业务层
 */
public interface OperateLogService {

    /**
     * 分页查询操作日志
     * @param queryParam 查询参数
     * @return 分页结果
     */
    PageResult<OperateLog> page(OperateLogQueryParam queryParam);
} 