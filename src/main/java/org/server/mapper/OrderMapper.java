package org.server.mapper;

import org.apache.ibatis.annotations.*;
import org.server.dto.OrderChart;
import org.server.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

	List<Order> findOrdersByTime(
			@Param("status") String status,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	List<OrderChart> getOrderChart();

	@Insert("insert ignore into order(eid, wid, pid, amount, status) " +
			"values (#{eid}, #{wid}, #{pid}, #{amount}, #{status})")
	Integer save(Order order);

	@Delete("delete from order where id=#{id}")
	Integer deleteById(Integer id);

	@Update("update order set status=#{status} where id=#{id}")
	Integer updateStatus(@Param("id") Integer id, @Param("status") String status);

}
