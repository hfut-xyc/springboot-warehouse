package org.server.service;

import org.server.dto.OrderChart;
import org.server.entity.Order;
import org.server.entity.Product;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.exception.OutOfStockException;
import org.server.exception.UpdateException;
import org.server.mapper.OrderMapper;
import org.server.mapper.ProductMapper;
import org.server.mapper.WarehouseMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private WarehouseMapper warehouseMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public List<Order> getOrderList(String status, String startTime, String endTime) {
        return orderMapper.getOrderList(status, startTime, endTime);
    }

    public List<OrderChart> getOrderChart() {
        return orderMapper.getOrderChart();
    }

    @Transactional
    public int addOrderWithOld(Order order) throws Exception {
        String warehousekey = "warehouse:" + order.getWid();
        String productKey = "product:" + order.getPid();
        Product product = (Product) redisTemplate.boundHashOps(warehousekey).get(productKey);
        // 如果缓存中存在该产品
        if (product != null) {
            int newAmount = product.getTotal() + order.getAmount();
            if (newAmount >= 0) {
                product.setTotal(newAmount);
                redisTemplate.boundHashOps(warehousekey).put(productKey, product);
                int res1 = warehouseMapper.updateProductByWid(order.getWid(), order.getPid(), newAmount);
                if (res1 != 1) {
                    throw new UpdateException("更新库存失败");
                }
            } else {
                throw new OutOfStockException("库存不足");
            }
        }
        // 如果缓存中不存在
        else {
            product = warehouseMapper.getProductByWidAndPid(order.getWid(), order.getPid());
            // 如果MySQL中本来就有库存记录，只是缓存中没有，执行update操作
            if (product != null) {
                int newAmount = product.getTotal() + order.getAmount();
                if (newAmount >= 0) {
                    product.setTotal(newAmount);
                    int res1 = warehouseMapper.updateProductByWid(order.getWid(), order.getPid(), newAmount);
                    if (res1 != 1) {
                        throw new UpdateException("更新库存失败");
                    }
                    redisTemplate.boundHashOps(warehousekey).put(productKey, product);
                } else {
                    throw new OutOfStockException("库存不足");
                }
            } else {    // 如果MySQL中没有库存记录，执行insert操作
                if (order.getAmount() < 0) {
                    throw new OutOfStockException("库存不足");
                }
                int res1 = warehouseMapper.addProductByWid(order.getWid(), order.getPid(), order.getAmount());
                if (res1 != 1) {
                    throw new InsertException("插入库存记录失败");
                }
                product = productMapper.getProductById(order.getPid());
                product.setTotal(order.getAmount());
                redisTemplate.boundHashOps(warehousekey).put(productKey, product);
            }
        }
        // 添加订单
        int res2 = orderMapper.addOrder(order);
        if (res2 != 1) {
            throw new InsertException("添加订单失败");
        }
        return 1;
    }

    @Transactional
    public int addOrderWithNew(Order order) throws InsertException {
        // 1.先添加订单
        int res1 = orderMapper.addOrder(order);
        if (res1 != 1) {
            throw new InsertException("添加订单失败");
        }
        // 2.insert一条库存记录，而不是update
        int res2 = warehouseMapper.addProductByWid(order.getWid(), order.getPid(), order.getAmount());
        if (res2 != 1) {
            throw new InsertException("插入库存记录失败");
        }
        // 3.更新缓存
        String warehousekey = "warehouse:" + order.getWid();
        String productKey = "product:" + order.getPid();
        Product product = productMapper.getProductById(order.getPid());
        product.setTotal(order.getAmount());
        redisTemplate.boundHashOps(warehousekey).put(productKey, product);
        return 1;
    }

    @Transactional
    public int deleteOrderById(int id) throws DeleteException {
        int res = orderMapper.deleteOrderById(id);
        if (res != 1) {
            throw new DeleteException("彻底删除订单失败");
        }
        return 1;
    }

    @Transactional
    public int updateOrderStatus(int id, String status) throws UpdateException {
        int res = orderMapper.updateOrderStatus(id, status);
        if (res != 1) {
            throw new UpdateException("订单状态修改失败");
        }
        return 1;
    }
}
