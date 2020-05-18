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
		int res = employeeMapper.addEmployee(employee);
		if (res != 1) {
			throw new InsertException("添加员工失败");
		}
		return 1;
	}

	@Transactional
	public int updateEmployeeInfo(Employee employee) {
		int res = employeeMapper.updateEmployeeInfo(employee);
		if (res != 1) {
			throw new UpdateException("修改员工信息失败");
		}
		return 1;
	}

	@Transactional
	public int updateWarehouseByEid(int eid, List<Integer> widList) {
		int res1 = employeeMapper.deleteAllWarehouseByEid(eid);
		int res2 = employeeMapper.addWarehouseByEid(eid, widList);
		if (widList.size() != res2) {
			throw new UpdateException("未能成功修改员工负责的仓库");
		}
		return 1;
	}

	@Transactional
	public int deleteEmployeeById(int id) throws DeleteException {
		int res1 = employeeMapper.deleteEmployeeById(id);
		int res2 = employeeMapper.deleteAllWarehouseByEid(id);
		if (res1 != 1) {
			throw new DeleteException("删除员工失败");
		}
		return 1;
	}
}
