package com.zcf.mapper;

import com.zcf.pojo.Student;
import com.zcf.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    
    /**
     * 根据条件查询学员列表
     * @param studentQueryParam 查询参数
     * @return 学员列表
     */
    List<Student> list(StudentQueryParam studentQueryParam);
    
    /**
     * 根据条件查询学员总数
     * @param studentQueryParam 查询参数
     * @return 总数
     */
    Long getTotal(StudentQueryParam studentQueryParam);

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

    @MapKey("name")
    List<Map<String, Object>> getStudentDegreeData();
}
