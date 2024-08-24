package com.admin.controller;

import com.admin.entity.Warehouse;
import com.admin.service.WarehouseService;
import com.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        Map<String, Object> map = warehouseService.listWarehouse(keyword);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Integer addWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        return warehouseService.insertWarehouse(warehouse);
    }

    @PutMapping("")
    public Integer updateWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        return warehouseService.updateWarehouse(warehouse);
    }

    @DeleteMapping("")
    public Integer deleteWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        return warehouseService.updateWarehouse(warehouse);
    }

}
