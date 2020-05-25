package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.Role;
import org.server.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

	List<User> getUserList(@Param("keyword") String keyword);

	List<Role> getRolesByUid(int uid);

	User getUserByUsername(String username);

	int addUser(User user);

	int deleteUserById(int id);

	int updateEnabledById(@Param("enabled") boolean enabled, @Param("id") int id);

	// 通过用户id添加角色
	int addRoleByUid(int uid, int rid);

	// 通过用户id删除角色
	int deleteRoleByUid(int uid, int rid);

	// 通过用户id删除其所有角色
	int deleteAllRoleByUid(int uid);
}
