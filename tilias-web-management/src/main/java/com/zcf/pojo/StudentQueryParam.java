package com.zcf.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StudentQueryParam {
    private String name; // 学员姓名
    private Integer degree; // 学历(1:初中,2:高中,3:大专,4:本科,5:硕士,6:博士)
    private Integer clazzId; // 班级ID
    private Integer page = 1; // 页码，默认1
    private Integer pageSize = 10; // 每页记录数，默认10
}
