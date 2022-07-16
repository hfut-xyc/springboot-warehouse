package org.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.server.entity.Product;
import org.server.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ProductController {

	@Resource
	private ProductService productService;

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

	@GetMapping("/product/id")
	public Integer getPidByName(@RequestParam(value = "name") String name) {
		return productService.getPidByName(name);
	}

	@PostMapping("/product/add")
	public int addProduct(@RequestBody Product product) {
		try {
			return productService.addProduct(product);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}
}
