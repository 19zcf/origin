package com.zcf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作日志查询参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLogQueryParam {
    private Integer page = 1;        // 页码，默认1
    private Integer pageSize = 10;   // 每页记录数，默认10
} 