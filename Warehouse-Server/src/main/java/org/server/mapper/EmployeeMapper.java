package org.server.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

	List<Employee> getEmployeeList(String keyword);

	int addEmployee(Employee employee);

	int addEmployeeWarehouse(int eid, List<Integer> widList);

	int updateEmployeeInfo(Employee employee);

	int deleteEmployee(int id);

	int deleteAllWarehouse(int eid);
}
