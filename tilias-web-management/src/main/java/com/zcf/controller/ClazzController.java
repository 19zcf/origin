package com.zcf.controller;

import com.zcf.pojo.Clazz;
import com.zcf.pojo.ClazzQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.pojo.Result;
import com.zcf.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理
 */
@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    
    @Autowired
    private ClazzService clazzService;
    
    /**
     * 班级列表查询
     * 该接口用于班级列表数据的条件分页查询
     * 
     * @param clazzQueryParam 查询参数
     * @return 分页结果
     */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询班级信息：{}", clazzQueryParam);
        
        // 参数校验和默认值设置
        if (clazzQueryParam.getPage() == null || clazzQueryParam.getPage() < 1) {
            clazzQueryParam.setPage(1);
        }
        if (clazzQueryParam.getPageSize() == null || clazzQueryParam.getPageSize() < 1) {
            clazzQueryParam.setPageSize(10);
        }
        
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加班级信息
     * @param clazz 班级信息
     * @return 操作结果
     */
    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("添加班级信息：{}", clazz);
        
        // 参数校验
        if (clazz.getName() == null || clazz.getName().trim().isEmpty()) {
            return Result.error("班级名称不能为空");
        }
        if (clazz.getRoom() == null || clazz.getRoom().trim().isEmpty()) {
            return Result.error("班级教室不能为空");
        }
        if (clazz.getBeginDate() == null) {
            return Result.error("开课时间不能为空");
        }
        if (clazz.getEndDate() == null) {
            return Result.error("结课时间不能为空");
        }
        if (clazz.getSubject() == null) {
            return Result.error("学科不能为空");
        }
        
        // 保存班级信息
        clazzService.save(clazz);
        return Result.success();
    }

    /**
     * 根据ID查询班级信息
     * @param id 班级ID
     * @return 班级信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询班级信息：{}", id);
        
        // 参数校验
        if (id == null || id <= 0) {
            return Result.error("班级ID不能为空或无效");
        }
        
        // 查询班级信息
        Clazz clazz = clazzService.getById(id);
        if (clazz == null) {
            return Result.error("班级不存在");
        }
        
        return Result.success(clazz);
    }

    /**
     * 修改班级信息
     * @param clazz 班级信息
     * @return 操作结果
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级信息：{}", clazz);
        
        // 参数校验
        if (clazz.getId() == null || clazz.getId() <= 0) {
            return Result.error("班级ID不能为空或无效");
        }
        if (clazz.getName() == null || clazz.getName().trim().isEmpty()) {
            return Result.error("班级名称不能为空");
        }
        if (clazz.getRoom() == null || clazz.getRoom().trim().isEmpty()) {
            return Result.error("班级教室不能为空");
        }
        if (clazz.getBeginDate() == null) {
            return Result.error("开课时间不能为空");
        }
        if (clazz.getEndDate() == null) {
            return Result.error("结课时间不能为空");
        }
        if (clazz.getMasterId() == null) {
            return Result.error("班主任ID不能为空");
        }
        
        // 检查班级是否存在
        Clazz existingClazz = clazzService.getById(clazz.getId());
        if (existingClazz == null) {
            return Result.error("班级不存在");
        }
        
        // 更新班级信息
        clazzService.update(clazz);
        return Result.success();
    }

    /**
     * 删除班级信息
     * @param id 班级ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级信息：{}", id);
        
        // 参数校验
        if (id == null || id <= 0) {
            return Result.error("班级ID不能为空或无效");
        }
        
        // 检查班级是否存在
        Clazz existingClazz = clazzService.getById(id);
        if (existingClazz == null) {
            return Result.error("班级不存在");
        }
        
        // 删除班级信息
        clazzService.delete(id);
        return Result.success();
    }

    /**
     * 查询所有班级信息
     * @return 班级列表
     */
    @GetMapping("/list")
    public Result listAll() {
        log.info("查询所有班级信息");
        List<Clazz> clazzList = clazzService.listAll();
        return Result.success(clazzList);
    }
}
