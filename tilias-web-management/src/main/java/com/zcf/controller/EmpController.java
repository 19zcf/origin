package com.zcf.controller;

import com.zcf.annotation.OperateLog;
import com.zcf.pojo.Emp;
import com.zcf.pojo.EmpQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.pojo.Result;
import com.zcf.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 员工管理
 */
@RestController
@Slf4j
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
//        log.info("分页查询员工信息，当前页码：{}，每页记录数：{}",page,pageSize);
//        log.info("员工信息，当前页码：{}，每页记录数：{}，姓名：{}，性别：{}，开始日期：{}，结束日期：{}",page,pageSize,name,gender,begin,end);
        log.info("员工信息：{}", empQueryParam);

        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);

    }

    /**
     * 查询全部员工信息
     * @return 员工列表
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询全部员工信息");
        List<Emp> empList = empService.list();
        return Result.success(empList);
    }

    @PostMapping
    @OperateLog(value = "添加员工", operateType = "增")
    public Result save(@RequestBody Emp emp){
        log.info("保存员工信息：{}", emp);
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping
    @OperateLog(value = "删除员工", operateType = "删")
    public Result delete(Integer[] ids){
        log.info("批量删除员工信息：{}", Arrays.toString(ids));
        empService.delete(ids);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("查询员工信息：{}", id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    @PutMapping
    @OperateLog(value = "修改员工", operateType = "改")
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息：{}", emp);
        empService.update(emp);
        return Result.success();
    }
}
