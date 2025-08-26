package com.zcf.controller;

import com.zcf.pojo.OperateLog;
import com.zcf.pojo.OperateLogQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.pojo.Result;
import com.zcf.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志管理
 */
@RestController
@Slf4j
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 分页查询操作日志
     * @param queryParam 查询参数
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result page(OperateLogQueryParam queryParam) {
        log.info("分页查询操作日志，参数：{}", queryParam);
        
        // 参数验证
        if (queryParam.getPage() == null || queryParam.getPage() < 1) {
            queryParam.setPage(1);
        }
        if (queryParam.getPageSize() == null || queryParam.getPageSize() < 1) {
            queryParam.setPageSize(10);
        }
        
        PageResult<OperateLog> pageResult = operateLogService.page(queryParam);
        return Result.success(pageResult);
    }
} 