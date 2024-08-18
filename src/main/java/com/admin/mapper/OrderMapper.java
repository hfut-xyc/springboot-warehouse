package com.admin.mapper;

import org.apache.ibatis.annotations.*;
import com.admin.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

	List<Order> list(@Param("startTime") String startTime, @Param("endTime") String endTime);

	@Insert("insert into tb_order(eid, wid, pid, amount, status) values (#{uid}, #{wid}, #{pid}, #{amount}, #{status})")
	Integer save(Order order);

	@Update("update tb_order set status=#{status} where id=#{id}")
	Integer update(Order order);

	@Delete("delete from tb_order where id=#{id}")
	Integer deleteById(Integer id);
}
