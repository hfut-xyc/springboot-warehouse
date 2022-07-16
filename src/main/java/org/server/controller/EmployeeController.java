package org.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.server.entity.Employee;
import org.server.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class EmployeeController {

	@Resource
	private EmployeeService employeeService;

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

	@PostMapping("/employee/add")
	public int addEmployee(@RequestBody Employee employee) {
		try {
			return employeeService.addEmployee(employee);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@PostMapping("/employee/{id}/update/info")
	public int updateEmployeeInfo(@RequestBody Employee employee) {
		try {
			return employeeService.updateEmployeeInfo(employee);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@PostMapping("/employee/{id}/update/warehouse")
	public int updateWarehouse(@PathVariable int id, @RequestBody Map<String, List<Integer>> map) {
		try {
			return employeeService.updateWarehouseByEid(id, map.get("widList"));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@DeleteMapping("/employee/{id}/delete")
	public int deleteEmployee(@PathVariable int id) {
		try {
			return employeeService.deleteEmployeeById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}


}
