package com.zcf.service;

import com.zcf.pojo.Clazz;
import com.zcf.pojo.ClazzQueryParam;
import com.zcf.pojo.PageResult;

import java.util.List;

/**
 * 班级业务层
 */
public interface ClazzService {

    /**
     * 分页查询班级信息
     * @param clazzQueryParam 查询参数
     * @return 分页结果
     */
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

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
     * 查询所有班级信息
     * @return 班级列表
     */
    List<Clazz> listAll();
}
