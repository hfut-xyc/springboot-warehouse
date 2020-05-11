package org.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.Warehouse;

@Mapper
public interface WarehouseMapper {

	Warehouse getWarehouseByName(String name);

    List<Warehouse> listWarehouseWithEmployee(@Param("keyword") String keyword);

    List<Warehouse> listWarehouseWithProduct(@Param("keyword") String keyword);

	int getWarehouseCount(@Param("keyword") String keyword);

	int addWarehouse(Warehouse warehouse);

	int deleteWarehouseById(int id);
}
