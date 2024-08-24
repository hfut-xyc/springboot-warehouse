package com.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.admin.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

	Integer count(String keyword);

	List<Product> listByName(String keyword);

	@Select("select count(*) from tb_product where name=#{name}")
	Product selectByName(String name);

	Integer insert(Product product);

	Integer update(Product product);

}
