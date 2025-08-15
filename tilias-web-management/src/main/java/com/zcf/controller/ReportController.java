package com.zcf.controller;


import com.zcf.pojo.ClazzOption;
import com.zcf.pojo.JobOption;
import com.zcf.pojo.Result;
import com.zcf.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计员工职位数据
     * @return
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位数据");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计员工性别数据
     * @return
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别数据");
        List<Map<String,Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    /**
     * 统计班级人数数据
     * @return 班级人数统计结果
     */

    @GetMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("统计班级人数数据");
        ClazzOption studentCountData = reportService.getStudentCountData();
        return Result.success(studentCountData);
    }

    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学生学历");
        List<Map<String,Object>> studentDegreeData = reportService.getStudentDegreeData();
        return Result.success(studentDegreeData);
    }

}
