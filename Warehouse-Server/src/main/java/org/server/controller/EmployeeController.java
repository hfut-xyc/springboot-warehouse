package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.entity.Employee;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "EmployeeController", description = "员工信息管理")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation("按页获取员工列表")
	@GetMapping("/employees")
	public Map<String, Object> getEmployeeList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword)
	{
		List<Employee> list = employeeService.getEmployeeList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		Map<String, Object> map = new HashMap<>();
		map.put("employeeList", list.subList(start, end));
		map.put("total",list.size());
		return map;
	}

	@ApiOperation("添加新员工")
	@PostMapping("/employee/add")
	public int addEmployee(@RequestBody Employee employee) {
		try {
			return employeeService.addEmployee(employee);
		} catch (InsertException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@ApiOperation("修改员工的基本信息")
	@PostMapping("/employee/{id}/update/info")
	public int updateEmployeeInfo(@RequestBody Employee employee) {
		try {
			return employeeService.updateEmployeeInfo(employee);
		} catch (InsertException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@ApiOperation("修改员工的管辖仓库")
	@PostMapping("/employee/{id}/update/warehouse")
	public int updateWarehouse(@PathVariable int id, @RequestBody Map<String, List<Integer>> map) {
		try {
			return employeeService.updateWarehouseByEid(id, map.get("widList"));
		} catch (InsertException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@ApiOperation("按id删除员工")
	@DeleteMapping("/employee/{id}/delete")
	public int deleteEmployee(@PathVariable int id) {
		try {
			return employeeService.deleteEmployeeById(id);
		} catch (DeleteException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}


}
