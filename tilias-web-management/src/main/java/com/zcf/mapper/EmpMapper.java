package com.zcf.mapper;

import com.zcf.pojo.Emp;
import com.zcf.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {

    /**
     * 查询员工总数
     * @return
     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long getTotal();


//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);



    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 查询全部员工信息
     * @return 员工列表
     */
    List<Emp> listAll();


    /**
     * 保存员工信息
     * @param emp
     */
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")//获取自增主键
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void save(Emp emp);

    /**
     * 根据id删除员工信息
     * @param ids
     */
    void deleteById(Integer[] ids);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Emp getById(Integer id);

    void update(Emp emp);

    /**
     * 统计员工职位人数
     * @return
     */
    @MapKey("position")
    List<Map<String,Object>> getJobList();

    /**
     * 统计员工性别人数
     * @return
     */
    @MapKey("name")
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 根据用户名和密码查询员工信息
     * @param emp
     * @return
     */
    @Select("select id,username,name from emp where username=#{username} and password=#{password}")
    Emp getByUsernameAndPassword(Emp emp);
}
