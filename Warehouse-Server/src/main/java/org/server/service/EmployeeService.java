package org.server.service;

import org.server.entity.Employee;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.exception.UpdateException;
import org.server.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeService {

	@Resource
	private EmployeeMapper employeeMapper;

	public List<Employee> getEmployeeList(String keyword) {
		return employeeMapper.getEmployeeList(keyword);
	}

	@Transactional
	public int addEmployee(Employee employee) {
		if (employeeMapper.addEmployee(employee) == 1) {
			return 1;
		} else {
			throw new InsertException("添加员工失败");
		}
	}

	@Transactional
	public int updateEmployeeInfo(Employee employee) {
		if (employeeMapper.updateEmployeeInfo(employee) == 1) {
			return 1;
		} else {
			throw new UpdateException("修改员工信息失败");
		}
	}

	@Transactional
	public int updateEmployeeWarehouse(int eid, List<Integer> widList) {
		employeeMapper.deleteAllWarehouse(eid);
		int res = employeeMapper.addEmployeeWarehouse(eid, widList);
		if (widList.size() == res) {
			return 1;
		} else {
			throw new UpdateException("未能成功修改员工负责的仓库");
		}
	}

	@Transactional
	public int deleteEmployee(int id) {
		if (employeeMapper.deleteEmployee(id) == 1) {
			return 1;
		} else {
			throw new DeleteException("删除员工失败");
		}
	}
}
