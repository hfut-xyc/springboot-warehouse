package org.server.mapper;

import org.apache.ibatis.annotations.*;
import org.server.entity.Role;
import org.server.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

	@Select("select * from user where username=#{username}")
	User findByUsername(String username);

	List<User> findUsers(@Param("keyword") String keyword);
	
	Role findRole(Integer id);

	@Insert("INSERT IGNORE INTO `user`(username, password) VALUES (#{username}, #{password})")
	Integer save(User user);

	@Delete("DELETE FROM `user` WHERE id=#{id}")
	Integer deleteUserById(Integer id);

	// 通过用户id添加角色
	@Insert("INSERT IGNORE INTO user_role VALUES (#{uid}, #{rid})")
	Integer addRoleById(Integer uid, Integer rid);

	// 通过用户id删除角色
	@Delete("DELETE FROM user_role WHERE uid=#{id}")
	Integer deleteRoleById(Integer id);

	Integer updateEnabledById(@Param("enabled") boolean enabled, @Param("id") Integer id);
}
