package com.zcf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcf.mapper.EmpExprMapper;
import com.zcf.mapper.EmpMapper;
import com.zcf.pojo.*;
import com.zcf.service.EmpLogService;
import com.zcf.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 使用分页插件
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());

        // 查询所有数据
        List<Emp> list=empMapper.list(empQueryParam);

        // 封装分页结果
        PageInfo<Emp> pageResult=new PageInfo<>(list);

        // 返回分页结果
        return new PageResult<>(pageResult.getTotal(),pageResult.getList());
    }

    @Override
    public List<Emp> list() {
        // 查询全部员工信息
        return empMapper.listAll();
    }

    /**
     * 保存员工信息
     * @param emp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        try {
            //保存员工的基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.save(emp);

            //保存员工的工作经历
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //设置员工id
                for (EmpExpr empExpr : exprList) {
                    empExpr.setEmpId(emp.getId());
                }
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            empLogService.insertLog(new EmpLog(emp.getId(),LocalDateTime.now(),"添加员工信息"+emp.getName()));
        }


    }

    /**
     * 删除员工信息
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer[] ids) {
        //1,批量删除员工基本信息
        empMapper.deleteById(ids);

        //2,批量删除员工工作经历
        empExprMapper.deleteByEmpId(ids);
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    /**
     * 修改员工信息
     * @param emp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1,修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        //2,修改员工工作经历
        //2.1 删除员工工作经历
        empExprMapper.deleteByEmpId(new Integer[]{emp.getId()});
        //2.2 添加员工工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            //设置员工id
            for (EmpExpr empExpr : exprList) {
                empExpr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(exprList);
        }
    }
}
