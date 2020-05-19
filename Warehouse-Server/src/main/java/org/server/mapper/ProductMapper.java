package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

	List<Product> getProductList(String keyword);

	Product getProductByName(String name);

	int addProduct(Product product);

}
