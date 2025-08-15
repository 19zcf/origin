package com.zcf.service;

import com.zcf.pojo.Emp;
import com.zcf.pojo.EmpQueryParam;
import com.zcf.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * 员工业务层
 */
@Service
public interface EmpService {

    /**
     * 分页查询员工信息
     * @param empQueryParam
     * @return
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 保存员工信息
     * @param emp
     */
    void save(Emp emp);

    void delete(Integer[] ids);

    Emp getById(Integer id);

    void update(Emp emp);
}
