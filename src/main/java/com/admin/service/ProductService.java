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
	public Map<String, Object> selectListByName(String keyword) {
		Integer count = productMapper.count(keyword);
		List<Product> productList = productMapper.selectListByName(keyword);
		productList.forEach(product -> {
			List<Warehouse> warehouseList = warehouseProductMapper.listWarehouse(product.getId());
			product.setWarehouseList(warehouseList);
		});

		Map<String, Object> map = new HashMap<>();
		map.put("total", count);
		map.put("productList", productList);
		return map;
	}

	/**
	 * 添加产品
	 * @param product
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Integer insert(Product product) throws Exception {
		Product temp = productMapper.selectByName(product.getName());
		if (temp != null) {
			throw new Exception("产品名已存在");
		}
		Integer res = productMapper.insert(product);
		if (res != 1) {
			throw new Exception("添加产品失败");
		}
		return 1;
	}

	@Transactional
	public Integer update(Product product) throws Exception  {
		Integer res = productMapper.update(product);
		if (res != 1) {
			throw new Exception("修改产品失败");
		}
		return 1;
	}


	@Transactional
	public Integer deleteById(Integer id) throws Exception  {
		Integer res = productMapper.deleteById(id);
		if (res != 1) {
			throw new Exception("删除产品失败");
		}
		return res;
	}
}
