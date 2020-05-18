package org.server.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

	List<Employee> getEmployeeList(String keyword);

	int addEmployee(Employee employee);

	int updateEmployeeInfo(Employee employee);

	int deleteEmployeeById(int id);

	int addWarehouseByEid(int eid, List<Integer> widList);

	int deleteAllWarehouseByEid(int eid);

}
