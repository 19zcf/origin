package com.zcf.service.impl;

import com.zcf.mapper.EmpMapper;
import com.zcf.pojo.JobOption;
import com.zcf.service.EmpService;
import com.zcf.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public JobOption getEmpJobData() {
        //1,调用Mapper接口
        List<Map<String,Object>> list = empMapper.getJobList();
        //2,封装结果,并返回
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("position")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList,dataList);
    }

    /**
     * 统计员工性别数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.getEmpGenderData();
    }
}
