package com.zcf.controller;

import com.zcf.pojo.Dept;
import com.zcf.pojo.Result;
import com.zcf.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    public Result list() {
        log.info("查询部门信息");
        List<Dept> list=deptService.findAll();
        return Result.success(list);
    }

    @RequestMapping(value = "/depts",method = RequestMethod.DELETE)
    public Result delete(Integer id ) {
        log.info("删除部门信息");
        deptService.deleteById(id);
        return Result.success();

    }

    @RequestMapping(value = "/depts",method = RequestMethod.POST)
    public Result save(@RequestBody Dept dept) {
        log.info("保存部门信息"+ dept);
        deptService.save(dept);
        return Result.success();
    }

    @RequestMapping(value = "/depts/{id}",method = RequestMethod.GET)
    public Result getInfo(@PathVariable("id") Integer id) {
        log.info("查询部门信息"+ id);
        Dept dept=deptService.getById(id);
        return Result.success(dept);
    }

    @RequestMapping(value = "depts",method = RequestMethod.PUT)
    public Result update(@RequestBody Dept dept) {
        log.info("更新部门信息"+ dept);
        deptService.update(dept);
        return Result.success();
    }
}
