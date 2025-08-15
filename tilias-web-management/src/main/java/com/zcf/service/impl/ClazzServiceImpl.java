package com.zcf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcf.mapper.ClazzMapper;
import com.zcf.pojo.Clazz;
import com.zcf.pojo.ClazzQueryParam;
import com.zcf.pojo.PageResult;
import com.zcf.service.ClazzService;
import com.zcf.exception.ClazzDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级业务层实现类
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        // 设置分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        
        // 查询班级列表
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
        
        // 计算每个班级的状态
        calculateClazzStatus(clazzList);
        
        // 获取分页信息
        PageInfo<Clazz> pageInfo = new PageInfo<>(clazzList);
        
        // 构建分页结果
        PageResult<Clazz> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(clazzList);
        
        return pageResult;
    }

    @Override
    public void save(Clazz clazz) {
        // 设置创建时间和更新时间
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        
        // 保存班级信息
        clazzMapper.save(clazz);
    }

    @Override
    public Clazz getById(Integer id) {
        // 根据ID查询班级信息
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        // 设置更新时间
        clazz.setUpdateTime(LocalDateTime.now());
        
        // 更新班级信息
        clazzMapper.update(clazz);
    }

    @Override
    public void delete(Integer id) {
        // 检查班级下是否有学生
        Integer studentCount = clazzMapper.countStudentsByClazzId(id);
        if (studentCount != null && studentCount > 0) {
            throw new ClazzDeleteException("对不起,该班级下有学生,不能直接删除");
        }
        
        // 删除班级信息
        clazzMapper.delete(id);
    }

    @Override
    public List<Clazz> listAll() {
        // 查询所有班级信息
        List<Clazz> clazzList = clazzMapper.listAll();
        
        // 计算每个班级的状态
        calculateClazzStatus(clazzList);
        
        return clazzList;
    }
    
    /**
     * 计算班级状态
     * 当前时间 > 结课时间：已结课
     * 当前时间 < 开班时间：未开班
     * 否则：在读中
     */
    private void calculateClazzStatus(List<Clazz> clazzList) {
        LocalDate currentDate = LocalDate.now();
        
        for (Clazz clazz : clazzList) {
            if (clazz.getBeginDate() != null && clazz.getEndDate() != null) {
                if (currentDate.isAfter(clazz.getEndDate())) {
                    clazz.setStatus("已结课");
                } else if (currentDate.isBefore(clazz.getBeginDate())) {
                    clazz.setStatus("未开班");
                } else {
                    clazz.setStatus("在读中");
                }
            } else {
                // 如果时间字段为空，设置为未知状态
                clazz.setStatus("未知");
            }
        }
    }
}
