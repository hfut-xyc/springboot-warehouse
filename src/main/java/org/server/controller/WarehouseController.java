package org.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.server.pojo.dto.Result;
import org.server.pojo.entity.Product;
import org.server.pojo.entity.Warehouse;
import org.server.service.WarehouseService;
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
    public Result getWarehouseList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<Warehouse> list = warehouseService.getWarehouseList(keyword);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("warehouseList", list.subList(start, end));
        return Result.ok(map, "查询成功");
    }

    @PostMapping("/")
    public int updateWarehouseInfo(@RequestBody Warehouse warehouse) {
        try {
            return warehouseService.updateWarehouseInfo(warehouse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }


    @GetMapping("/warehouse/{id}/products")
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

    @PostMapping("/warehouse/")
    public int addWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        try {
            return warehouseService.addWarehouse(warehouse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

}
