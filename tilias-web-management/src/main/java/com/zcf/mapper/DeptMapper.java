package com.zcf.mapper;

import com.zcf.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门
     * @return
     */
    //手动映射
   /* @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime")
    })
    @Select("select id,name,create_time,update_time from dept order by update_time desc ")
    List<Dept> findAll();*/

    @Select("select id,name,create_time ,update_time  from dept order by update_time desc ")
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     */
    @Delete("delete from dept where id=#{id}")
    void deleteById(Integer id);

    /**
     * 添加部门
     * @param dept
     */
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void save(Dept dept);

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @Select("select id,name,create_time,update_time from dept where id=#{id}")
    Dept getById(Integer id);

    /**
     * 修改部门
     * @param dept
     */
    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void update(Dept dept);
}
