package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.dto.OrderChart;
import org.server.entity.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

	List<Order> getOrderList(@Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);

	List<OrderChart> getOrderChart();

	int addOrder(Order order);

	int deleteOrderById(int id);

	int updateOrderStatus(@Param("id") int id, @Param("status") String status);

}
