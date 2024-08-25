package com.admin.service;

import com.admin.entity.Product;
import com.admin.entity.Warehouse;
import com.admin.mapper.ProductMapper;
import com.admin.mapper.WarehouseProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

	@Resource
	private ProductMapper productMapper;

	@Resource
	private WarehouseProductMapper warehouseProductMapper;

	/**
	 * 根据产品名分页查询产品以及所在的仓库
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> listProduct(String keyword) {
		Integer count = productMapper.count(keyword);
		List<Product> productList = productMapper.listByName(keyword);
		productList.forEach(product -> {
			List<Warehouse> warehouseList = warehouseProductMapper.listWarehouse(product.getId());
			product.setWarehouseList(warehouseList);
		});

		Map<String, Object> map = new HashMap<>();
		map.put("total", count);
		map.put("productList", productList);
		return map;
	}

	//public Integer getPidByName(String name) {
	//	return productMapper.getPidByName(name);
	//}
	// 商品不应该直接主动添加，而是随着入库订单而出现新的商品
	// 商品没必要有删除操作，如果某一商品在所有仓库的合计数量告罄，直接显示0，方便后续补货

	@Transactional
	public int insertProduct(Product product) throws Exception {
		Product temp = productMapper.selectByName(product.getName());
		if (temp != null) {
			throw new Exception("产品名已存在");
		}
		int res = productMapper.insert(product);
		if (res != 1) {
			throw new Exception("添加产品失败");
		}
		return 1;
	}

}
