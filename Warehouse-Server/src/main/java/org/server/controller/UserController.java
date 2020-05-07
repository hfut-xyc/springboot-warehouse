package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.entity.User;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.exception.RepeatException;
import org.server.exception.UpdateException;
import org.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "UserController", description = "用户信息管理")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation("按页获取用户列表")
	@GetMapping("/users")
	public Map<String, Object> getUserList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword)
	{
		int total = userService.getUserCount(keyword);
		List<User> list = userService.getUserList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		// 此处返回的total和userList的长度并不一定相等，userList的长度仅表示一页的数据量
		Map<String, Object> map = new HashMap<>();
		map.put("userList", list.subList(start, end));
		map.put("total",total);
		return map;
	}

	@ApiOperation("添加新用户")
	@PostMapping("/user/add")
	public int addUser(@RequestBody User user) {
		try {
			userService.addUser(user);
			return 1;
		} catch (RepeatException | InsertException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@ApiOperation("按id启用或禁用账户")
	@PostMapping("/user/{id}/enabled")
	public int updateUserEnabled(@RequestParam("enabled") boolean enabled, @PathVariable int id) {
		try {
			userService.updateUserEnabled(enabled, id);
			return 1;
		} catch (UpdateException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@ApiOperation("按id设置用户是否为管理员")
	@PostMapping("/user/{id}/set-admin")
	public int updateUserRole(@RequestParam("isAdmin") boolean enabled, @PathVariable int id) {
		try {
			userService.updateUserRole(enabled, id);
			return 1;
		} catch (UpdateException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@ApiOperation("按id删除用户")
	@DeleteMapping("/user/{id}/delete")
	public int deleteUserById(@PathVariable int id) {
		try {
			userService.deleteUserById(id);
			return 1;
		} catch (DeleteException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
}
