package com.zcf.service.impl;

import com.zcf.mapper.OperateLogMapper;
import com.zcf.pojo.OperateLog;
import com.zcf.pojo.OperateLogQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志业务层实现类
 */
@Service
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLog> page(OperateLogQueryParam queryParam) {
        log.info("分页查询操作日志，参数：{}", queryParam);
        
        // 计算分页偏移量
        Integer start = (queryParam.getPage() - 1) * queryParam.getPageSize();
        
        // 查询总数
        Long total = operateLogMapper.getTotal();
        
        // 查询数据列表
        List<OperateLog> rows = operateLogMapper.page(queryParam.getPageSize(), start);
        
        // 构建分页结果
        PageResult<OperateLog> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setRows(rows);
        
        return pageResult;
    }
} 