package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

	List<Employee> getEmployeeList(String keyword);

	int addEmployee(Employee employee);

	int updateEmployee(Employee employee);

	int deleteEmployee(int id);
}
