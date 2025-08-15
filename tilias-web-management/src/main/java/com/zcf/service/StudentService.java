package com.zcf.service;

import com.zcf.pojo.Student;
import com.zcf.pojo.StudentQueryParam;
import com.zcf.pojo.PageResult;

import java.util.List;

/**
 * 学员业务层
 */
public interface StudentService {

    /**
     * 分页查询学员信息
     * @param studentQueryParam 查询参数
     * @return 分页结果
     */
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    /**
     * 保存学员信息
     * @param student 学员信息
     */
    void save(Student student);

    /**
     * 根据ID查询学员信息
     * @param id 学员ID
     * @return 学员信息
     */
    Student getById(Integer id);

    /**
     * 修改学员信息
     * @param student 学员信息
     */
    void update(Student student);

    /**
     * 批量删除学员信息
     * @param ids 学员ID数组
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 处理学员违纪
     * @param id 学员ID
     * @param score 扣除分数
     */
    void handleViolation(Integer id, Integer score);
} 