package com.admin.service;

import com.admin.entity.Order;
import com.admin.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 根据时间分页查询订单
     * @param page
     * @param pageSize
     * @param date
     * @return
     */
    public Map<String, Object> selectListByDate(Integer page, Integer pageSize, String date) {
        Integer count = orderMapper.countByDate(date);
        List<Order> orderList = orderMapper.selectListByDate(page, pageSize, date);

        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("orderList", orderList);
        return map;
    }


    @Transactional
    public Integer insert(Order order) throws Exception {
        
        return 1;
    }

    @Transactional
    public Integer deleteById(Integer id) throws Exception {
        Integer res = orderMapper.deleteById(id);
        if (res != 1) {
            throw new Exception("删除订单失败");
        }
        return res;
    }

}
