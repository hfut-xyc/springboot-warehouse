package com.admin.mapper;

import com.admin.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

	Integer count(String keyword);

	List<Product> selectListByName(String keyword);

	@Select("select id from tb_product where name=#{name}")
	Product selectByName(String name);

	@Insert("insert into tb_product(name) values (#{name});")
	Integer insert(Product product);

	@Update("update tb_product set name=#{name} where id=#{id}")
	Integer update(Product product);

	@Delete("delete from tb_product where id=#{id}")
	Integer deleteById(Integer id);
}
