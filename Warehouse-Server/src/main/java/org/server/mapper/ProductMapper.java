package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

	// 根据名称模糊查询商品
	List<Product> getProductList(String keyword);

	int addProduct(Product product);

	int deleteProductById(int id);

}
