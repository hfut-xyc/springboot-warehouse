package org.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.server.entity.Product;
import org.server.entity.Warehouse;
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
    public Map<String, Object> getWarehouseList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<Warehouse> list = warehouseService.getWarehouseList(keyword);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("warehouseList", list.subList(start, end));
        map.put("total", list.size());
        return map;
    }

    @PostMapping("/warehouse/update/info")
    public int updateWarehouseInfo(@RequestBody Warehouse warehouse) {
        try {
            return warehouseService.updateWarehouseInfo(warehouse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @PostMapping("/warehouse/{id}/employees")
    public int updateEmployeeByWid(@PathVariable int id, @RequestBody List<Integer> eidList) {
        try {
            return warehouseService.updateEmployeeByWid(id, eidList);
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

    @PostMapping("/warehouse/add")
    public int addWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        try {
            return warehouseService.addWarehouse(warehouse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

}
