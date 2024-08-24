package com.admin.mapper;

import org.apache.ibatis.annotations.*;
import com.admin.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    Integer count(String startTime, String endTime);

	List<Order> listByCreateTime(
        @Param("page") Integer page,
        @Param("pageSize") Integer pageSize,
        @Param("startTime") String startTime,
        @Param("endTime") String endTime
    );

    @Insert("insert into tb_order(eid, wid, pid, count, status) values (#{uid}, #{wid}, #{pid}, #{count}, #{status})")
    Integer insert(Order order);

    @Delete("delete from tb_order where id=#{id}")
    Integer deleteById(Integer id);

}
