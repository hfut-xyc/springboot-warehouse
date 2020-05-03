package org.server.controller;

import org.server.entity.Role;
import org.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/roles")
	public List<Role> getRoleList() {
		return roleService.getRoleList();
	}

}
