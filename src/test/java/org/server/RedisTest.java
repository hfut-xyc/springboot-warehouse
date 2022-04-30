package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.Product;
import org.server.mapper.WarehouseMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private WarehouseMapper warehouseMapper;

    @Test
    void test() {
        List<Product> list = warehouseMapper.getProductListById(1);
        for (Product item : list) {
            redisTemplate.boundHashOps("warehouse:1").put("product:" + item.getId(), item);
        }
    }

}
