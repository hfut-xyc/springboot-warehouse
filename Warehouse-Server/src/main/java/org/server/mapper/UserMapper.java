package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

	User getUserById(int id);

	User getUserByUsername(String username);

	List<User> getUserList(@Param("offset") int offset, @Param("count") int count, @Param("keyword") String keyword);

	int getUserCount(@Param("keyword") String keyword);

	int addUser(User user);

	int updateUserEnabled(@Param("enabled") boolean enabled, @Param("id") int id);

	int deleteUserById(int id);
}
