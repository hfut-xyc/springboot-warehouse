package com.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.admin.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

	List<Product> getProductList(String keyword);

	Integer getPidByName(String name);

	@Select("select * from tb_product where id=#{id}")
	Product getProductById(int id);

	@Select("select * from tb_product where name=#{name}")
	Product findByName(String name);

	int addProduct(Product product);

}
