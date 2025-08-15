package com.zcf.mapper;

import com.zcf.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    /**
     * 批量插入员工eper
     * @param exprList
     */
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id批量删除eper
     * @param ids
     */
    void deleteByEmpId(Integer[] ids);
}

