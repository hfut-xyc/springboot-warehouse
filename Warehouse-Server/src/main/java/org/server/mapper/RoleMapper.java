package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

	List<Role> getRolesByUid(int uid);

	int addUserRole(int uid, int rid);

	int deleteRole(int uid, int rid);

	int deleteAllRole(int uid);
}
