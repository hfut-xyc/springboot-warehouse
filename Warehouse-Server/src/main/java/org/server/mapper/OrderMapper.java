package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

	List<Order> getOrderList(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	int addOrder(Order order);

	int deleteOrderById(int id);
}
