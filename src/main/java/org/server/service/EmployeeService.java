package org.server.service;

import org.server.entity.Employee;
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
		return employeeMapper.findEmployees(keyword);
	}

	@Transactional
	public int addEmployee(Employee employee) throws Exception {
		int res = employeeMapper.save(employee);
		if (res != 1) {
			throw new Exception("添加员工失败");
		}
		return 1;
	}

	@Transactional
	public int updateEmployeeInfo(Employee employee) throws Exception {
		int res = employeeMapper.updateEmployeeInfo(employee);
		if (res != 1) {
			throw new Exception("修改员工信息失败");
		}
		return 1;
	}

	@Transactional
	public int updateWarehouseByEid(int eid, List<Integer> widList) throws Exception  {
		employeeMapper.deleteAllWarehouseByEid(eid);
		if (widList.size() == 0) {
			return 1;
		}
		int res = employeeMapper.addWarehouseByEid(eid, widList);
		if (widList.size() != res) {
			throw new Exception("修改员工仓库失败");
		}
		return 1;
	}

	@Transactional
	public int deleteEmployeeById(int id) throws Exception {
		int res1 = employeeMapper.deleteById(id);
		employeeMapper.deleteAllWarehouseByEid(id);
		if (res1 != 1) {
			throw new Exception("删除员工失败");
		}
		return 1;
	}
}
