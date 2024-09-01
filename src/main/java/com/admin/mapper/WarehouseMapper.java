package com.admin.mapper;

import com.admin.entity.Warehouse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseMapper {

	Integer count(String keyword);

	List<Warehouse> selectListByName(@Param("keyword") String keyword);

	@Select("select id from tb_warehouse where name=#{name}")
	Warehouse selectByName(String name);

	@Insert("insert into tb_warehouse(name) values (#{name})")
	Integer insert(Warehouse warehouse);

	@Update("update tb_warehouse set name=#{name} where id=#{id}")
	Integer update(Warehouse warehouse);

	@Delete("delete from tb_warehouse where id=#{id}")
	Integer deleteById(Integer id);

}
