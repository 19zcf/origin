package com.zcf.service.impl;

import com.zcf.mapper.EmpMapper;
import com.zcf.mapper.ClazzMapper;
import com.zcf.mapper.StudentMapper;
import com.zcf.pojo.ClazzOption;
import com.zcf.pojo.JobOption;
import com.zcf.service.EmpService;
import com.zcf.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private StudentMapper studentMapper;

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

    @Override
    public ClazzOption getStudentCountData() {
        // 1. 调用Mapper接口获取班级人数统计数据
        List<Map<String, Object>> countList = clazzMapper.getStudentCountData();
        
        // 2. 封装结果，分离班级名称和人数数据
       List<Object> classList= countList.stream().map(dataMap -> dataMap.get("clazzlist")).toList();
       List<Object> dataList= countList.stream().map(dataMap -> dataMap.get("datalist")).toList();
        
        // 3. 构建返回结果
        return new ClazzOption(classList,dataList);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.getStudentDegreeData();
    }
}
