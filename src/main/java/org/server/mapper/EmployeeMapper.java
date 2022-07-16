package org.server.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.server.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<Employee> findEmployees(String keyword);

    @Insert("INSERT INTO employee(name, gender, phone, salary) " +
            "VALUES (#{name}, #{gender}, #{phone}, #{salary})")
    Integer save(Employee employee);

    @Update("UPDATE employee " +
            "SET name=#{name}, gender=#{gender}, phone=#{phone}, salary=#{salary} " +
            "WHERE id = #{id}")
    Integer updateEmployeeInfo(Employee employee);

    @Delete("delete from employee where id=#{id}")
    Integer deleteById(Integer id);

    Integer addWarehouseByEid(Integer eid, List<Integer> widList);

    Integer deleteAllWarehouseByEid(Integer eid);

}
