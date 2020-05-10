package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Role;

import java.util.List;

@Mapper
public interface UserRoleMapper {

	List<Role> getRolesByUid(int uid);

	int clearRolesByUid(int uid);

	int addUserRole(int uid, int rid);

	int deleteUserRole(int uid, int rid);
}
