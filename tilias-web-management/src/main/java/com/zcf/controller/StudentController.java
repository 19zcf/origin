package com.zcf.controller;

import com.zcf.annotation.OperateLog;
import com.zcf.pojo.Student;
import com.zcf.pojo.StudentQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.pojo.Result;
import com.zcf.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 学员管理
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    /**
     * 学员列表查询
     * 该接口用于学员列表数据的条件分页查询
     * 
     * @param studentQueryParam 查询参数
     * @return 分页结果
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询学员信息：{}", studentQueryParam);
        
        // 参数校验和默认值设置
        if (studentQueryParam.getPage() == null || studentQueryParam.getPage() < 1) {
            studentQueryParam.setPage(1);
        }
        if (studentQueryParam.getPageSize() == null || studentQueryParam.getPageSize() < 1) {
            studentQueryParam.setPageSize(10);
        }
        
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加学员信息
     * @param student 学员信息
     * @return 操作结果
     */
    @PostMapping
    @OperateLog(value = "添加学员", operateType = "增")
    public Result save(@RequestBody Student student) {
        log.info("添加学员信息：{}", student);
        
        // 参数校验
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return Result.error("学员姓名不能为空");
        }
        if (student.getNo() == null || student.getNo().trim().isEmpty()) {
            return Result.error("学号不能为空");
        }
        if (student.getGender() == null) {
            return Result.error("性别不能为空");
        }
        if (student.getPhone() == null || student.getPhone().trim().isEmpty()) {
            return Result.error("手机号不能为空");
        }
        if (student.getDegree() == null) {
            return Result.error("学历不能为空");
        }
        if (student.getClazzId() == null) {
            return Result.error("班级ID不能为空");
        }
        
        // 保存学员信息
        studentService.save(student);
        return Result.success();
    }

    /**
     * 根据ID查询学员信息
     * @param id 学员ID
     * @return 学员信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询学员信息：{}", id);
        
        // 参数校验
        if (id == null || id <= 0) {
            return Result.error("学员ID不能为空或无效");
        }
        
        // 查询学员信息
        Student student = studentService.getById(id);
        if (student == null) {
            return Result.error("学员不存在");
        }
        
        return Result.success(student);
    }

    /**
     * 修改学员信息
     * @param student 学员信息
     * @return 操作结果
     */
    @PutMapping
    @OperateLog(value = "修改学员", operateType = "改")
    public Result update(@RequestBody Student student) {
        log.info("修改学员信息：{}", student);
        
        // 参数校验
        if (student.getId() == null || student.getId() <= 0) {
            return Result.error("学员ID不能为空或无效");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return Result.error("学员姓名不能为空");
        }
        if (student.getNo() == null || student.getNo().trim().isEmpty()) {
            return Result.error("学号不能为空");
        }
        if (student.getGender() == null) {
            return Result.error("性别不能为空");
        }
        if (student.getPhone() == null || student.getPhone().trim().isEmpty()) {
            return Result.error("手机号不能为空");
        }
        if (student.getDegree() == null) {
            return Result.error("学历不能为空");
        }
        if (student.getClazzId() == null) {
            return Result.error("班级ID不能为空");
        }
        if (student.getViolationCount() == null) {
            return Result.error("违纪次数不能为空");
        }
        if (student.getViolationScore() == null) {
            return Result.error("违纪扣分不能为空");
        }
        
        // 检查学员是否存在
        Student existingStudent = studentService.getById(student.getId());
        if (existingStudent == null) {
            return Result.error("学员不存在");
        }
        
        // 更新学员信息
        studentService.update(student);
        return Result.success();
    }

    /**
     * 批量删除学员信息
     * @param ids 学员ID数组，格式：1,2,3
     * @return 操作结果
     */
    @DeleteMapping("/{ids}")
    @OperateLog(value = "删除学员", operateType = "删")
    public Result deleteByIds(@PathVariable String ids) {
        log.info("批量删除学员信息：{}", ids);
        
        // 参数校验
        if (ids == null || ids.trim().isEmpty()) {
            return Result.error("学员ID不能为空");
        }
        
        try {
            // 解析ID字符串，支持逗号分隔的多个ID
            List<Integer> idList = new ArrayList<>();
            String[] idArray = ids.split(",");
            
            for (String idStr : idArray) {
                if (idStr.trim().isEmpty()) {
                    continue;
                }
                Integer id = Integer.parseInt(idStr.trim());
                if (id <= 0) {
                    return Result.error("学员ID必须大于0");
                }
                idList.add(id);
            }
            
            if (idList.isEmpty()) {
                return Result.error("没有有效的学员ID");
            }
            
            // 批量删除学员信息
            studentService.deleteByIds(idList);
            return Result.success();
            
        } catch (NumberFormatException e) {
            log.error("学员ID格式错误：{}", ids);
                         return Result.error("学员ID格式错误，请使用逗号分隔的数字，如：1,2,3");
         }
     }

    /**
     * 处理学员违纪
     * @param id 学员ID
     * @param score 扣除分数
     * @return 操作结果
     */
    @PutMapping("/violation/{id}/{score}")
    @OperateLog(value = "处理学员违纪扣分", operateType = "改")
    public Result handleViolation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("处理学员违纪：学员ID={}, 扣分={}", id, score);
        
        // 参数校验
        if (id == null || id <= 0) {
            return Result.error("学员ID不能为空或无效");
        }
        if (score == null || score <= 0) {
            return Result.error("扣分必须大于0");
        }
        
        try {
            // 处理学员违纪
            studentService.handleViolation(id, score);
            return Result.success();
            
        } catch (RuntimeException e) {
            log.error("处理学员违纪失败：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
