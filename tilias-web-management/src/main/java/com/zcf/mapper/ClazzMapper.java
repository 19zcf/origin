package com.zcf.mapper;

import com.zcf.pojo.Clazz;
import com.zcf.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper {
    
    /**
     * 根据条件查询班级列表
     * @param clazzQueryParam 查询参数
     * @return 班级列表
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);
    
    /**
     * 根据条件查询班级总数
     * @param clazzQueryParam 查询参数
     * @return 总数
     */
    Long getTotal(ClazzQueryParam clazzQueryParam);

    /**
     * 保存班级信息
     * @param clazz 班级信息
     */
    void save(Clazz clazz);

    /**
     * 根据ID查询班级信息
     * @param id 班级ID
     * @return 班级信息
     */
    Clazz getById(Integer id);

    /**
     * 修改班级信息
     * @param clazz 班级信息
     */
    void update(Clazz clazz);

    /**
     * 删除班级信息
     * @param id 班级ID
     */
    void delete(Integer id);

    /**
     * 检查班级下是否有学生
     * @param clazzId 班级ID
     * @return 学生数量
     */
    Integer countStudentsByClazzId(Integer clazzId);

    /**
     * 查询所有班级信息
     * @return 班级列表
     */
    List<Clazz> listAll();

    /**
     * 统计每个班级的人数
     * @return 班级人数统计列表
     */
    @MapKey("")
    List<Map<String, Object>> getStudentCountData();
}
