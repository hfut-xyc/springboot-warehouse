package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.entity.Product;
import org.server.exception.InsertException;
import org.server.exception.RepeatException;
import org.server.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "ProductController", description = "产品管理")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation("按页获取产品列表")
	@GetMapping("/products")
	public Map<String, Object> getUserList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword)
	{
		List<Product> list = productService.getProductList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		Map<String, Object> map = new HashMap<>();
		map.put("productList", list.subList(start, end));
		map.put("total", list.size());
		return map;
	}

	@ApiOperation("添加新产品")
	@PostMapping("/product/add")
	public int addProduct(@RequestBody Product product) {
		try {
			productService.addProduct(product);
		} catch (RepeatException | InsertException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}
}
