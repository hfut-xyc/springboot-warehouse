package org.server.service;

import org.server.entity.Employee;
import org.server.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class EmployeeService {

	@Resource
	private EmployeeMapper employeeMapper;

	public List<Employee> getEmployeeList(String keyword) {
		return employeeMapper.getEmployeeList(keyword);
	}

	public int getEmployeeCount(String keyword) {
		return employeeMapper.getEmployeeCount(keyword);
	}
}
