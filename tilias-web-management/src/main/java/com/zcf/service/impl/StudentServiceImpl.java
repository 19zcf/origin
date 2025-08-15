package com.zcf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcf.mapper.StudentMapper;
import com.zcf.pojo.Student;
import com.zcf.pojo.StudentQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;

/**
 * 学员业务层实现类
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        // 设置分页参数
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        
        // 查询学员列表
        List<Student> studentList = studentMapper.list(studentQueryParam);
        
        // 获取分页信息
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        
        // 构建分页结果
        PageResult<Student> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(studentList);
        
        return pageResult;
    }

    @Override
    public void save(Student student) {
        // 设置创建时间和更新时间
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        
        // 设置默认值
        if (student.getViolationCount() == null) {
            student.setViolationCount((short) 0);
        }
        if (student.getViolationScore() == null) {
            student.setViolationScore((short) 0);
        }
        if (student.getIsCollege() == null) {
            student.setIsCollege(0);
        }
        
        // 保存学员信息
        studentMapper.save(student);
    }

    @Override
    public Student getById(Integer id) {
        // 根据ID查询学员信息
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        // 设置更新时间
        student.setUpdateTime(LocalDateTime.now());
        
        // 更新学员信息
        studentMapper.update(student);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        // 批量删除学员信息
        studentMapper.deleteByIds(ids);
    }

    @Override
    public void handleViolation(Integer id, Integer score) {
        // 处理学员违纪
        // 1. 检查学员是否存在
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new RuntimeException("学员不存在");
        }
        
        // 2. 检查扣分是否合理
        if (score == null || score <= 0) {
            throw new RuntimeException("扣分必须大于0");
        }
        
        // 3. 执行违纪处理（增加违纪次数和扣分，并更新更新时间）
        studentMapper.handleViolation(id, score);
    }
} 