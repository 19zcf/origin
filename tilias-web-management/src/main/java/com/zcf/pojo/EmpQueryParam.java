package com.zcf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpQueryParam {
    private Integer page=1;//当前页码
    private Integer pageSize=10;//每页显示的记录数
    private String name;// 姓名
    private Integer gender;// 性别
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;// 入职日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;// 离职日期

}
