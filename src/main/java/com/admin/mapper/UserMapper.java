package com.admin.mapper;

import com.admin.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

	Integer count(String keyword);

	List<User> listByName(
		@Param("page") Integer page,
		@Param("pageSize") Integer pageSize,
		@Param("keyword") String keyword
	);

	@Select("select username, password from tb_user where username=#{username}")
	User selectByName(String username);

	@Insert("insert into tb_user(username, password) VALUES (#{username}, #{password})")
	Integer insert(User user);

	@Update("update tb_user set password=#{password}, role=#{role} where id=#{id}")
	Integer update(User user);

	@Delete("delete from tb_user where id=#{id}")
	Integer deleteById(Integer id);

}
