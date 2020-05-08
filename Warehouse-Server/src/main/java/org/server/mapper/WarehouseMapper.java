package org.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.Warehouse;

@Mapper
public interface WarehouseMapper {

	Warehouse getWarehouseByName(String name);

	// Warehouse getWarehouseById(int id);

    List<Warehouse> getWarehouseList(@Param("keyword") String keyword);

	int getWarehouseCount(@Param("keyword") String keyword);

	int addWarehouse(Warehouse warehouse);

	int deleteWarehouseById(int id);
}