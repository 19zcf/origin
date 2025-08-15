package com.zcf.service;

import com.zcf.pojo.ClazzOption;
import com.zcf.pojo.JobOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ReportService {

    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计班级人数数据
     * @return 班级人数统计结果
     */
    ClazzOption getStudentCountData();

    List<Map<String, Object>> getStudentDegreeData();
}
