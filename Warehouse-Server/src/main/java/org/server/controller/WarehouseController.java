package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.server.entity.Product;
import org.server.entity.Warehouse;
import org.server.exception.*;
import org.server.service.WarehouseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "WarehouseController", description = "仓库信息管理")
@RestController
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation("按页获取仓库列表")
	@GetMapping("/warehouses")
	public Map<String, Object> getWarehouseList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword)
	{
		List<Warehouse> list = warehouseService.getWarehouseList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		Map<String, Object> map = new HashMap<>();
		map.put("warehouseList", list.subList(start, end));
		map.put("total", list.size());
		return map;
	}

	@ApiOperation("修改仓库基本信息")
	@PostMapping("/warehouse/update/info")
	public int updateWarehouseInfo(@RequestBody Warehouse warehouse) {
		try {
			return warehouseService.updateWarehouseInfo(warehouse);
		} catch (UpdateException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@ApiOperation("修改仓库管理员")
	@PostMapping("/warehouse/{wid}/employees")
	public int updateEmployeeByWid(@PathVariable int wid, @RequestBody List<Integer> eidList) {
		try {
			return warehouseService.updateEmployeeByWid(wid, eidList);
		} catch (UpdateException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}
	
	@ApiOperation("按仓库id获取其存放的商品列表，并且分页")
	@GetMapping("/warehouse/{id}/products")
	public Map<String, Object> getProductListById(
		@PathVariable int id,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize)
	{
		List<Product> list = warehouseService.getProductListById(id);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		Map<String, Object> map = new HashMap<>();
		map.put("productList", list.subList(start, end));
		map.put("total", list.size());
		return map;
	}

    @ApiOperation("添加新仓库")
	@PostMapping("/warehouse/add")
	public int addWarehouse(@RequestBody Warehouse warehouse) {
		try {
			return warehouseService.addWarehouse(warehouse);
		} catch (RepeatException | InsertException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

}
