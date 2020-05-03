package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.Role;
import org.server.entity.User;

import java.util.List;

@Mapper
public interface RoleMapper {

	List<Role> getRolesByUid(int uid);

	List<Role> getRoleList();

	int addUserRole(int uid, int rid);

	int deleteUserRole(int uid, int rid);

}
