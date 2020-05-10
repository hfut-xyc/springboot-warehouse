package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.entity.Warehouse;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.exception.RepeatException;
import org.server.exception.UpdateException;
import org.server.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		int total = warehouseService.getWarehouseCount(keyword);
		List<Warehouse> list = warehouseService.getWarehouseList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());

		// total是总量，warehouseList是仅仅是请求数据
		Map<String, Object> map = new HashMap<>();
		map.put("warehouseList", list.subList(start, end));
		map.put("total",total);
		return map;
	}

    @ApiOperation("添加新仓库")
	@PostMapping("/warehouse/add")
	public int addWarehouse(@RequestBody Warehouse warehouse) {
		try {
			warehouseService.addWarehouse(warehouse);
			return 1;
		} catch (RepeatException | InsertException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}


	@ApiOperation("按id删除仓库")
	@DeleteMapping("/warehouse/{id}/delete")
	public int deleteWarehouseById(@PathVariable int id) {
		try {
			warehouseService.deleteWarehouseById(id);
			return 1;
		} catch (DeleteException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

}
