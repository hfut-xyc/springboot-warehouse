package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

	List<Role> getRolesByUid(int uid);

	int addRoleByUid(int uid, int rid);

	int deleteRoleByUid(int uid, int rid);

	int clearRolesByUid(int uid);
}
