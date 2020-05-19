package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.Product;
import org.server.entity.Warehouse;
import org.server.mapper.WarehouseMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class WarehouseTest {

    @Resource
    private WarehouseMapper warehouseMapper;

    @Test
    void getWarehouseList() {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        List<Warehouse> list = warehouseMapper.getWarehouseList(null);
        stopwatch.stop();
        System.out.println(stopwatch.getLastTaskTimeMillis());
        System.out.println(list);
    }

    @Test
    void addWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("合肥工业大仓库");
        warehouseMapper.addWarehouse(warehouse);
    }

    @Test
    void getProductListById() {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        List<Product> list = warehouseMapper.getProductListById(1);
        stopwatch.stop();
        System.out.println(stopwatch.getLastTaskTimeMillis());
        System.out.println(list);
    }

    @Test
    void getProductStock() {
        Integer amount = warehouseMapper.getProductStockByWid(1, 20000);
        System.out.println(amount);
    }
    // passed, nothing failed. 

}
