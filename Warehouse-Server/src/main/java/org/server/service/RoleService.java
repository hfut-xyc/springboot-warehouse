package org.server.service;

import org.server.entity.Role;
import org.server.entity.User;
import org.server.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

	@Resource
	private RoleMapper roleMapper;

	public List<Role> getRoleList() {
		return roleMapper.getRoleList();
	}

}
