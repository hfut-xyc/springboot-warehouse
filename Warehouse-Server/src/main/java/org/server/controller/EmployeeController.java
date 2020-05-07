package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.entity.Employee;
import org.server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "EmployeeController", description = "员工信息管理")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@ApiOperation("按页获取员工列表")
	@GetMapping("/employees")
	public Map<String, Object> getEmployeeList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword)
	{
		int total = employeeService.getEmployeeCount(keyword);
		List<Employee> list = employeeService.getEmployeeList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		// 此处返回的total和userList的长度并不一定相等，userList的长度仅表示一页的数据量
		Map<String, Object> map = new HashMap<>();
		map.put("userList", list.subList(start, end));
		map.put("total",total);
		return map;
	}
}
