package com.admin.controller;

import lombok.extern.slf4j.Slf4j;
import com.admin.vo.Result;
import com.admin.entity.Product;
import com.admin.entity.Warehouse;
import com.admin.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public Result listWarehouse(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        List<Warehouse> list = warehouseService.getWarehouseList(keyword);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("warehouseList", list.subList(start, end));
        return Result.ok("查询成功", map);
    }

    @GetMapping("/{id}/products")
    public Map<String, Object> getProductListById(
            @PathVariable int id,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws Exception {
        List<? super Product> list = warehouseService.getProductListById(id);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("productList", list.subList(start, end));
        map.put("total", list.size());
        return map;
    }

    @PostMapping("")
    public Integer addWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        return warehouseService.insert(warehouse);
    }

    @PutMapping("")
    public Integer updateWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        return warehouseService.updateWarehouseInfo(warehouse);
    }

    @DeleteMapping("")
    public Integer deleteWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        return warehouseService.updateWarehouseInfo(warehouse);
    }

}
