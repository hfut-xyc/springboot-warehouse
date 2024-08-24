package com.admin.mapper;

import com.admin.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseMapper {

	Integer count(String keyword);

	List<Warehouse> listByName(@Param("keyword") String keyword);

	Warehouse selectByName(String name);

	// 添加仓库
	int insert(Warehouse warehouse);

	// 修改仓库
	int update(Warehouse warehouse);
}
