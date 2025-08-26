package com.zcf.mapper;

import com.zcf.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

    /**
     * 分页查询操作日志
     */
    List<OperateLog> page(@Param("pageSize") Integer pageSize, @Param("start") Integer start);

    /**
     * 统计操作日志总数
     */
    @Select("select count(*) from operate_log")
    Long getTotal();
}
