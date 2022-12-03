package org.server.mapper;

import org.apache.ibatis.annotations.*;
import org.server.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

	List<User> page(@Param("keyword") String keyword);

	@Select("select * from user where username=#{username}")
	User findByUsername(String username);

	@Select("select * from user where username=#{username} and password=#{password}")
	User findByUsernameAndPasswd(@Param("username") String username, @Param("password") String password);

	@Insert("insert into user(username, password) VALUES (#{username}, #{password})")
	Integer save(User user);

	@Update("update user set password=#{password}, status=#{status}, admin=#{admin} where id=#{id}")
	Integer update(User user);

	@Delete("delete from `user` where id=#{id}")
	Integer deleteById(Integer id);

}
