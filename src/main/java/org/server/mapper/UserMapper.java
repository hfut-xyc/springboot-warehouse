package org.server.mapper;

import org.apache.ibatis.annotations.*;
import org.server.pojo.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

	List<User> list(@Param("keyword") String keyword);

	@Select("select id, username, password from tb_user where username=#{username}")
	User findByUsername(String username);

	@Insert("insert into tb_user(username, password) VALUES (#{username}, #{password})")
	Integer save(User user);

	@Update("update tb_user set password=#{password}where id=#{id}")
	Integer update(User user);

	@Delete("delete from tb_user where id=#{id}")
	Integer deleteById(Integer id);

}
