package org.server.service;

import org.server.entity.Product;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.exception.RepeatException;
import org.server.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {

	@Resource
	private ProductMapper productMapper;

	public List<Product> getProductList(String keyword) {
		return productMapper.getProductList(keyword);
	}

	public Integer getPidByName(String name) {
		return productMapper.getPidByName(name);
	}

	// 商品不应该直接主动添加，而是随着入库订单而出现新的商品

	// 商品没必要有删除操作，如果某一商品在所有仓库的合计数量告罄，直接显示0，方便后续补货

	@Transactional
	public int addProduct(Product product) throws RepeatException, InsertException {
		Product temp = productMapper.getProductByName(product.getName());
		if (temp != null) {
			throw new RepeatException("产品名已存在");
		}
		int res = productMapper.addProduct(product);
		if (res != 1) {
			throw new InsertException("添加产品失败");
		}
		return 1;
	}

}
